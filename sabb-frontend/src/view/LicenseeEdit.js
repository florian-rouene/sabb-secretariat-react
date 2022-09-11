import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

class LicenseeEdit extends Component {

    emptyItem = {
        name: '',
        numLicensee: '',
        firstname: '',
        phone: '',
        mail: '',
        adress: '',        
        assoName: '',
        categoryId: null,
        sex: 'F',
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem,
            categories: [],
            teams: [],
            cancelLink: "/licensees"
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleCheckboxChange = this.handleCheckboxChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {       
        fetch('/categories')
        .then(response => response.json())
        .then(data => this.setState({categories: data}));
        fetch('/teams')
        .then(response => response.json())
        .then(data => this.setState({teams: data}));
    }

    handleChange(event) {
        const target = event.target;        
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    handleCheckboxChange(event) {
        const target = event.target;        
        const name = target.name;
        let item = {...this.state.item};
        item[name] = !item[name];
        this.setState({item});
    }

async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;

    await fetch('/licensees' + (item.id ? '/' + item.id : ''), {
        method: (item.id) ? 'PUT' : 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(item),
    });
    this.props.history.push(this.state.cancelLink);
}

    render() {
        const {item} = this.state;
        const title = <h2>{item.id ? 'Edit Licensee' : 'Add Licensee'}</h2>;

        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="name">Numéro de licence</Label>
                        <Input type="text" name="numLicensee" id="numLicensee" value={item.numLicensee || ''}
                               onChange={this.handleChange} autoComplete="numLicensee"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="name">Name</Label>
                        <Input type="text" name="name" id="name" value={item.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="name">Prénom</Label>
                        <Input type="text" name="firstname" id="firstname" value={item.firstname || ''}
                               onChange={this.handleChange} autoComplete="firstname"/>
                    </FormGroup>  
                    <FormGroup>
                        <Input type="select" name="categoryId" id="categoryId" value={item.categoryId || null} onChange={this.handleChange} autoComplete="categoryId">
                            {
                                this.state.categories.map(category => { 
                                    return <option value={category.id}>{category.name}</option>;
                                })
                            }
                        </Input>
                    </FormGroup>
                    <FormGroup>
                        <Input type="select" name="sex" id="sex" value={item.sex || 'F'} onChange={this.handleChange} autoComplete="sex">
                             <option value='F'>Féminin</option>
                             <option value='M'>Masculin</option>
                        </Input>
                    </FormGroup>
                    
                    <FormGroup>
                        <Label for="name">Téléphone</Label>
                        <Input type="text" name="phone" id="phone" value={item.phone || ''}
                               onChange={this.handleChange} autoComplete="phone"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="name">Mail</Label>
                        <Input type="text" name="mail" id="mail" value={item.mail || ''}
                               onChange={this.handleChange} autoComplete="mail"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="name">Adresse</Label>
                        <Input type="text" name="adress" id="adress" value={item.adress || ''}
                               onChange={this.handleChange} autoComplete="adress"/>
                    </FormGroup>
                                      
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(LicenseeEdit);