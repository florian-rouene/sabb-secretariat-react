import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class SaisonList extends Component {
  constructor(props) {
    super(props);
    this.state = {saisons: []};
    this.remove = this.remove.bind(this);
}

componentDidMount() {
    fetch('/saisons')
        .then(response => response.json())
        .then(data => this.setState({saisons: data}));
}

async remove(id) {
    await fetch(`/saisons/${id}`, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(() => {
        let updatedSaisons = [...this.state.saisons].filter(i => i.id !== id);
        this.setState({saisons: updatedSaisons});
    });
}

render() {
    const {saisons} = this.state;

    const saisonsList = saisons.map(saison => {
        return <tr key={saison.id}>
            <td style={{whiteSpace: 'nowrap'}}>{saison.name}</td>
            <td>{saison.referenceYear}</td>
            <td>
                <ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={"/saisons/" + saison.id}>Edit</Button>
                    <Button size="sm" color="danger" onClick={() => this.remove(saison.id)}>Delete</Button>
                </ButtonGroup>
            </td>
        </tr>
    });

    return (
        <div>
            <AppNavbar/>
            <Container fluid>
                <div className="float-right">
                    <Button color="success" tag={Link} to="/saisons/new">Add saison</Button>
                </div>
                <h3>Saisons</h3>
                <Table className="mt-4">
                    <thead>
                    <tr>
                        <th width="30%">Name</th>
                        <th width="30%">Année de référence</th>
                        <th width="40%">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {saisonsList}
                    </tbody>
                </Table>
            </Container>
        </div>
    );
}
}

export default SaisonList;