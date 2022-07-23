import React, { Component } from 'react';
import '../index.css';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';

class Home extends Component {
    render() {
        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <Button color="link"><Link to="/associations">Association</Link></Button>
                    <Button color="link"><Link to="/categories">Categorie</Link></Button>
                    <Button color="link"><Link to="/sub_categories">Sous-Categorie</Link></Button>
                    <Button color="link"><Link to="/equipes">Equipe</Link></Button>                   
                </Container>
            </div>
        );
    }
}

export default Home;