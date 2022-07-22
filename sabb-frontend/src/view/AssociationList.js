import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class AssociationList extends Component {
  constructor(props) {
    super(props);
    this.state = {associations: []};
    this.remove = this.remove.bind(this);
}

componentDidMount() {
    fetch('/associations')
        .then(response => response.json())
        .then(data => this.setState({associations: data}));
}

async remove(id) {
    await fetch(`/associations/${id}`, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(() => {
        let updatedAssociations = [...this.state.associations].filter(i => i.id !== id);
        this.setState({associations: updatedAssociations});
    });
}

render() {
    const {associations} = this.state;

    const associationsList = associations.map(association => {
        return <tr key={association.id}>
            <td style={{whiteSpace: 'nowrap'}}>{association.name}</td>
            <td>{association.nameFfbb}</td>
            <td>
                <ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={"/associations/" + association.id}>Edit</Button>
                    <Button size="sm" color="danger" onClick={() => this.remove(association.id)}>Delete</Button>
                    <Button color="link"><Link to={"/ffbb_equipes/" + association.nameFfbb}>Equipe FFBB</Link></Button>
                </ButtonGroup>
            </td>
        </tr>
    });

    return (
        <div>
            <AppNavbar/>
            <Container fluid>
                <div className="float-right">
                    <Button color="success" tag={Link} to="/associations/new">Add association</Button>
                </div>
                <h3>Associations</h3>
                <Table className="mt-3">
                    <thead>
                    <tr>
                        <th width="30%">Name</th>
                        <th width="30%">FFBB name</th>
                        <th width="40%">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {associationsList}
                    </tbody>
                </Table>
            </Container>
        </div>
    );
}
}

export default AssociationList;