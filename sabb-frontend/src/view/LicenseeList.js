import React, { Component } from 'react';
import {  Button, ButtonGroup, Input, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import 'react-calendar/dist/Calendar.css';
import { v4 as uuid } from 'uuid';
import { Link } from 'react-router-dom';

class LicenseeList extends Component {
  constructor(props) {
    super(props);
    this.state = {licensees: [], unfilteredLicensees: [], equipes: [], selectedFile: undefined};    
    this.filterByTeam = this.filterByTeam.bind(this);
    this.onFileChangeHandler = this.onFileChangeHandler.bind(this);
}

componentDidMount() {
    fetch('/licensees')
        .then(response => response.json())
        .then(data => this.setState({licensees: data, unfilteredLicensees: data}));
    fetch('/equipes')
        .then(response => response.json())
        .then(data => this.setState({equipes: data}));
}



filterByTeam(event) {
    let selectedValue = Number(event.target.value);
    if (selectedValue !== -1) {
        let filteredLicensee = [...this.state.unfilteredLicensees].filter(m => m.teamId === selectedValue);
        this.setState({licensees: filteredLicensee});
    } else {
        this.setState({licensees: this.state.unfilteredLicensees});
    }
}

onFileChangeHandler = (e) => {
    e.preventDefault();
    console.log(e.target.files[0])
    this.setState({
        selectedFile: e.target.files[0]
    });
    const formData = new FormData();
    formData.append('file', e.target.files[0]);
    fetch('/licensees/upload', {
        method: 'post',
        body: formData
    }).then(res => {
        if(res.ok) {
            console.log(res.data);
            alert("File uploaded successfully.")
        }
    });
};

async remove(id) {
    await fetch(`/licensee/${id}`, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(() => {
        let updatedLicensees = [...this.state.licensees].filter(i => i.id !== id);
        this.setState({licensees: updatedLicensees});
    });
}

render() {
    const {licensees} = this.state;

    const licenseesList = licensees.map(licensee => {
        return <tr key={licensee.id === 0 ? uuid() : licensee.id}>
            <td>{licensee.numLicensee}</td>
            <td>{licensee.name}</td>
            <td>{licensee.firstname}</td>
            <td style={{whiteSpace: 'nowrap'}}>{this.state.equipes.filter(e => e.id === licensee.teamId).map(e => e.name)}</td>
            <td>
                <ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={"/licensee/" + licensee.id}>Edit</Button>
                    <Button size="sm" color="danger" onClick={() => this.remove(licensee.id)}>Delete</Button>
                </ButtonGroup>
            </td>
            </tr>
    });

    return (
        <div>
            <AppNavbar/>
            <Container fluid>               
                <h1>Licenciés</h1>
                <h2>Filtres</h2>
                <Input type="select" name="teamFilter" onChange={this.filterByTeam}>
                    <option value={-1}></option>
                    {                        
                        this.state.equipes.map(equipe => { 
                            return <option value={equipe.id}>{equipe.name}</option>;
                        })
                    }
                </Input>       
                <h2>Upload Your File </h2>
                <input type="file" className="form-control" name="file" onChange={this.onFileChangeHandler}/>         
                <h2>Résultats</h2>
                <Table className="mt-4">
                    <thead>
                    <tr>
                        <th width="20%">Num licence</th>
                        <th width="30%">Nom</th>
                        <th width="20%">Prénom</th>
                        <th width="20%">Equipe</th>
                        <th width="10%">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {licenseesList}
                    </tbody>
                </Table>
            </Container>
        </div>
    );
}
}

export default LicenseeList;