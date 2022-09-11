import React, { Component } from 'react';
import { Button, Input, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import DateObject from "react-date-object";
import { v4 as uuid } from 'uuid';
import LicenseesPopover from './component/LicenseesPopover';

class OfficialList extends Component {
  constructor(props) {
    super(props);
    this.state = {officials: [], unfilteredOfficials: [], equipes: [], licensees: []};    
    this.filterByTeam = this.filterByTeam.bind(this);
    this.filterByDate = this.filterByDate.bind(this);
}

componentDidMount() {
    fetch('/officials')
        .then(response => response.json())
        .then(data => this.setState({officials: data, unfilteredOfficials: data}));
    fetch('/equipes')
        .then(response => response.json())
        .then(data => this.setState({equipes: data}));
    fetch('/licensees')
        .then(response => response.json())
        .then(data => this.setState({licensees: data, unfilteredLicensees: data}));
}



filterByTeam(event) {
    let selectedValue = Number(event.target.value);
    if (selectedValue !== -1) {
        let filteredOfficial = [...this.state.unfilteredOfficials].filter(m => m.teamId === selectedValue);
        this.setState({officials: filteredOfficial});
    } else {
        this.setState({officials: this.state.unfilteredOfficials});
    }
}

filterByDate(event) {
    let filteredOfficial = [...this.state.unfilteredOfficials].filter(m => new DateObject(m.matchDate).weekOfYear === event);
    this.setState({officials: filteredOfficial});
}

render() {
    const {officials} = this.state;

    const officialsList = officials.map(official => {
        return <tr key={official.id === 0 ? uuid() : official.id}>
            <td style={{whiteSpace: 'nowrap'}}>{this.state.equipes.filter(e => e.id === official.teamId).map(e => e.name)}</td>
            <td>{official.opponent}</td>
            <td>{official.matchDate}</td>
            <td><span>{official.licenseeTable1Id}</span><LicenseesPopover match={official.match} licensees={this.state.licensees} equipes={this.state.equipes}/></td>
            </tr>
    });

    return (
        <div>
            <AppNavbar/>
            <Container fluid>               
                <h1>Officials</h1>
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
                        <th width="10%">Equipe</th>
                        <th width="20%">Adversaire</th>
                        <th width="20%">Date</th>
                        <th width="50%">Officiel</th>
                    </tr>
                    </thead>
                    <tbody>
                    {officialsList}
                    </tbody>
                </Table>
            </Container>
        </div>
    );
}
}

export default OfficialList;