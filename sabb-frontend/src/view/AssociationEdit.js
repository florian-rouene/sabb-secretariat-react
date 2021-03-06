import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

class AssociationEdit extends Component {

    emptyItem = {
        name: '',
        main: false,
        nameFfbb: '',
        nameFfbbCtc: '',
        ffbbLocation: '',
        ffbbLocationBis: '',
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleCheckboxChange = this.handleCheckboxChange.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const association = await (await fetch(`/associations/${this.props.match.params.id}`)).json();
            this.setState({item: association});
        }
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

    await fetch('/associations' + (item.id ? '/' + item.id : ''), {
        method: (item.id) ? 'PUT' : 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(item),
    });
    this.props.history.push('/associations');
}

    render() {
        const {item} = this.state;
        const title = <h2>{item.id ? 'Edit Association' : 'Add Association'}</h2>;

        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="name">Name</Label>
                        <Input type="text" name="name" id="name" value={item.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="main">Principale</Label>
                        <Input type="checkbox" name="main" id="main" checked={item.main || false}
                               onChange={this.handleCheckboxChange} autoComplete="main"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="nameFfbb">Nom FFBB</Label>
                        <Input type="text" name="nameFfbb" id="nameFfbb" value={item.nameFfbb || ''}
                               onChange={this.handleChange} autoComplete="nameFfbb"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="nameFfbbCtc">Nom FFBB de la CTC</Label>
                        <Input type="text" name="nameFfbbCtc" id="nameFfbbCtc" value={item.nameFfbbCtc || ''}
                               onChange={this.handleChange} autoComplete="nameFfbbCtc"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="ffbbLocation">Adresse principale</Label>
                        <Input type="text" name="ffbbLocation" id="ffbbLocation" value={item.ffbbLocation || ''}
                               onChange={this.handleChange} autoComplete="ffbbLocation"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="ffbbLocationBis">Adresse secondaire</Label>
                        <Input type="text" name="ffbbLocationBis" id="ffbbLocationBis" value={item.ffbbLocationBis || ''}
                               onChange={this.handleChange} autoComplete="ffbbLocationBis"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/associations">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(AssociationEdit);