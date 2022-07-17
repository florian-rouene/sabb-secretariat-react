import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class EquipeList extends Component {
  constructor(props) {
    super(props);
    this.state = {equipes: []};
    this.remove = this.remove.bind(this);
}

componentDidMount() {
    fetch('/equipes')
        .then(response => response.json())
        .then(data => this.setState({equipes: data}));
}

async remove(id) {
    await fetch(`/equipes/${id}`, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(() => {
        let updatedEquipes = [...this.state.equipes].filter(i => i.id !== id);
        this.setState({equipes: updatedEquipes});
    });
}

render() {
    const {equipes} = this.state;

    const equipesList = equipes.map(equipe => {
        return <tr key={equipe.id}>
            <td style={{whiteSpace: 'nowrap'}}>{equipe.name}</td>
            <td>{equipe.nameFfbb}</td>
            <td>
                <ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={"/equipes/" + equipe.id}>Edit</Button>
                    <Button size="sm" color="danger" onClick={() => this.remove(equipe.id)}>Delete</Button>
                </ButtonGroup>
            </td>
        </tr>
    });

    return (
        <div>
            <AppNavbar/>
            <Container fluid>
                <div className="float-right">
                    <Button color="success" tag={Link} to="/equipes/new">Add equipe</Button>
                </div>
                <h3>Equipes</h3>
                <Table className="mt-4">
                    <thead>
                    <tr>
                        <th width="30%">Name</th>
                        <th width="30%">FFBB name</th>
                        <th width="40%">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {equipesList}
                    </tbody>
                </Table>
            </Container>
        </div>
    );
}
}

export default EquipeList;