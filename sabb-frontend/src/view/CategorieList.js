import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class CategorieList extends Component {
  constructor(props) {
    super(props);
    this.state = {categories: []};
    this.remove = this.remove.bind(this);
}

componentDidMount() {
    fetch('/categories')
        .then(response => response.json())
        .then(data => this.setState({categories: data}));
}

async remove(id) {
    await fetch(`/categories/${id}`, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(() => {
        let updatedCategories = [...this.state.categories].filter(i => i.id !== id);
        this.setState({categories: updatedCategories});
    });
}

render() {
    const {categories} = this.state;

    const categoriesList = categories.map(categorie => {
        return <tr key={categorie.id}>
            <td style={{whiteSpace: 'nowrap'}}>{categorie.name}</td>
            <td>{categorie.autobind?"oui":"non"}</td>
            <td>
                <ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={"/categories/" + categorie.id}>Edit</Button>
                    <Button size="sm" color="danger" onClick={() => this.remove(categorie.id)}>Delete</Button>
                </ButtonGroup>
            </td>
        </tr>
    });

    return (
        <div>
            <AppNavbar/>
            <Container fluid>
                <div className="float-right">
                    <Button color="success" tag={Link} to="/categories/new">Add categorie</Button>
                </div>
                <h3>Categories</h3>
                <Table className="mt-4">
                    <thead>
                    <tr>
                        <th width="30%">Name</th>
                        <th width="30%">Autobind</th>
                        <th width="40%">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {categoriesList}
                    </tbody>
                </Table>
            </Container>
        </div>
    );
}
}

export default CategorieList;