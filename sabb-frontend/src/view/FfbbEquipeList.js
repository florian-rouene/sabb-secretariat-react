import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class FfbbEquipeList extends Component {
  constructor(props) {
    super(props);
    this.state = {ffbbEquipes: []};
    
}

componentDidMount() {
    fetch(`/ffbb_equipes/${this.props.match.params.ffbbName}`)
        .then(response => response.json())
        .then(data => this.setState({ffbbEquipes: data}));
}


render() {
    const {ffbbEquipes} = this.state;

    const ffbbEquipesList = ffbbEquipes.map(equipe => {
        return <tr key={equipe.id}>
            <td style={{whiteSpace: 'nowrap'}}>{equipe.name}</td>
            <td>{equipe.poule}</td>
            <td>
                <ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={`/equipes/ffbb/${equipe.ffbbUniqueId}/${equipe.name}/${this.props.match.params.ffbbName}`}>Bind</Button>                    
                </ButtonGroup>
            </td>
        </tr>
    });

    return (
        <div>
            <AppNavbar/>
            <Container fluid>                
                <h3>Equipes FFBB</h3>
                <Table className="mt-4">
                    <thead>
                    <tr>
                        <th width="30%">Name</th>
                        <th width="30%">Poule</th>
                        <th width="40%">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {ffbbEquipesList}
                    </tbody>
                </Table>
            </Container>
        </div>
    );
}
}

export default FfbbEquipeList;