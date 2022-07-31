import React, { Component } from 'react';
import { Button, Input, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import DateObject from "react-date-object";

class MatchList extends Component {
  constructor(props) {
    super(props);
    this.state = {matchs: [], unfilteredMatchs: [], equipes: []};
    this.reloadAllMatchs = this.reloadAllMatchs.bind(this);
    this.filterByTeam = this.filterByTeam.bind(this);
    this.filterByDate = this.filterByDate.bind(this);
}

componentDidMount() {
    fetch('/matchs')
        .then(response => response.json())
        .then(data => this.setState({matchs: data, unfilteredMatchs: data}));
    fetch('/equipes')
        .then(response => response.json())
        .then(data => this.setState({equipes: data}));
}

async reloadAllMatchs() {
    await fetch(`/matchs/reload`, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(response => response.json())
    .then(data => this.setState({matchs: data}));
}

filterByTeam(event) {
    let selectedValue = Number(event.target.value);
    if (selectedValue !== -1) {
        let filteredMatch = [...this.state.unfilteredMatchs].filter(m => m.teamId === selectedValue);
        this.setState({matchs: filteredMatch});
    } else {
        this.setState({matchs: this.state.unfilteredMatchs});
    }
}

filterByDate(event) {
    let filteredMatch = [...this.state.unfilteredMatchs].filter(m => new DateObject(m.matchDate).weekOfYear === event);
    this.setState({matchs: filteredMatch});
}

render() {
    const {matchs} = this.state;

    const matchsList = matchs.map(match => {
        return <tr key={match.id}>
            <td style={{whiteSpace: 'nowrap'}}>{this.state.equipes.filter(e => e.id === match.teamId).map(e => e.name)}</td>
            <td>{match.opponent}</td>
            <td>{match.matchDate}</td>
            </tr>
    });

    return (
        <div>
            <AppNavbar/>
            <Container fluid>
                <div className="float-right">
                    <Button size="sm" color="danger" onClick={() => this.reloadAllMatchs()}>Recharger tous les matchs</Button>
                </div>
                <h1>Matchs</h1>
                <h2>Filtres</h2>
                <Input type="select" name="teamFilter" onChange={this.filterByTeam}>
                    <option value={-1}></option>
                    {                        
                        this.state.equipes.map(equipe => { 
                            return <option value={equipe.id}>{equipe.name}</option>;
                        })
                    }
                </Input>
                <Calendar showWeekNumbers={true} onClickWeekNumber={this.filterByDate}/>
                <h2>Résultats</h2>
                <Table className="mt-4">
                    <thead>
                    <tr>
                        <th width="30%">Equipe</th>
                        <th width="30%">Adversaire</th>
                        <th width="40%">Date</th>
                    </tr>
                    </thead>
                    <tbody>
                    {matchsList}
                    </tbody>
                </Table>
            </Container>
        </div>
    );
}
}

export default MatchList;