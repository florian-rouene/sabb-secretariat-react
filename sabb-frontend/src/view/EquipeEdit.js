import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

class EquipeEdit extends Component {

    emptyItem = {
        name: '',
        ctc: false,
        ffbbUniqueId: '',        
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id === 'ffbb') {
            let item = {...this.state.item};
            item["ffbbUniqueId"] = this.props.match.params.ffbbId;
            console.log( this.props);
            this.setState({item});
        } else if (this.props.match.params.id !== 'new') {
            const equipe = await (await fetch(`/equipes/${this.props.match.params.id}`)).json();
            this.setState({item: equipe});
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

async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;

    await fetch('/equipes' + (item.id ? '/' + item.id : ''), {
        method: (item.id) ? 'PUT' : 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(item),
    });
    this.props.history.push('/equipes');
}

    render() {
        const {item} = this.state;
        const title = <h2>{item.id ? 'Edit Equipe' : 'Add Equipe'}</h2>;

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
                        <Label for="main">ctc</Label>
                        <Input type="checkbox" name="main" id="main" value={item.ctc || ''}
                               onChange={this.handleChange} autoComplete="main"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="ffbbUniqueId">FFBB code</Label>
                        <Input type="text" name="ffbbUniqueId" id="ffbbUniqueId" value={item.ffbbUniqueId || ''}
                               onChange={this.handleChange} autoComplete="ffbbUniqueId"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/equipes">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(EquipeEdit);