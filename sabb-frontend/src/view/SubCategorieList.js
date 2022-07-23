import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class SubCategorieList extends Component {
  constructor(props) {
    super(props);
    this.state = {subcategories: []};
    this.remove = this.remove.bind(this);
}

componentDidMount() {
    fetch('/sub_categories')
        .then(response => response.json())
        .then(data => this.setState({subcategories: data}));
}

async remove(id) {
    await fetch(`/sub_categories/${id}`, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(() => {
        let updatedSubCategories = [...this.state.subcategories].filter(i => i.id !== id);
        this.setState({subcategories: updatedSubCategories});
    });
}

render() {
    const {subcategories} = this.state;

    const subcategoriesList = subcategories.map(subcategorie => {
        return <tr key={subcategorie.id}>
            <td style={{whiteSpace: 'nowrap'}}>{subcategorie.name}</td>
            <td>{subcategorie.sex}</td>
            <td>
                <ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={"/sub_categories/" + subcategorie.id}>Edit</Button>
                    <Button size="sm" color="danger" onClick={() => this.remove(subcategorie.id)}>Delete</Button>
                </ButtonGroup>
            </td>
        </tr>
    });

    return (
        <div>
            <AppNavbar/>
            <Container fluid>
                <div className="float-right">
                    <Button color="success" tag={Link} to="/sub_categories/new">Add subcategorie</Button>
                </div>
                <h3>SubCategories</h3>
                <Table className="mt-4">
                    <thead>
                    <tr>
                        <th width="30%">Name</th>
                        <th width="30%">Sexe</th>
                        <th width="40%">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {subcategoriesList}
                    </tbody>
                </Table>
            </Container>
        </div>
    );
}
}

export default SubCategorieList;