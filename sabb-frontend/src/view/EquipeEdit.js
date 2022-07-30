import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

class EquipeEdit extends Component {

    emptyItem = {
        name: '',
        ctc: false,
        ffbbUniqueId: '',
        sort:0,
        excelReference: '',
        excelReferenceCtc: '',
        refereeReplacmentLabel: '',
        hasOfficialReferee: false,
        assoName: '',
        categoryId: null,
        sex: '',
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem,
            categories: [],
            cancelLink: "/equipes"
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleCheckboxChange = this.handleCheckboxChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id === 'ffbb') {
            let item = {...this.state.item};
            item["ffbbUniqueId"] = this.props.match.params.ffbbId;
            item["name"] = this.props.match.params.name;
            item["assoName"] = this.props.match.params.asso;
            console.log( this.props);
            this.setState({item, cancelLink: `/ffbb_equipes/${this.props.match.params.asso}`});
        } else if (this.props.match.params.id !== 'new') {
            const equipe = await (await fetch(`/equipes/${this.props.match.params.id}`)).json();
            this.setState({item: equipe});
        }
        fetch('/categories')
        .then(response => response.json())
        .then(data => this.setState({categories: data}));
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

    await fetch('/equipes' + (item.id ? '/' + item.id : ''), {
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
                        <Input type="select" name="categoryId" id="categoryId" value={item.categoryId || null} onChange={this.handleChange} autoComplete="categoryId">
                            {
                                this.state.categories.map(category => { 
                                    return <option value={category.id}>{category.name}</option>;
                                })
                            }
                        </Input>
                    </FormGroup>
                    <FormGroup>
                        <Input type="select" name="sex" id="sex" value={item.sex || ''} onChange={this.handleChange} autoComplete="sex">
                             <option value='F'>Féminin</option>
                             <option value='M'>Masculin</option>
                        </Input>
                    </FormGroup>
                    <FormGroup>
                        <Label for="ctc">CTC</Label>
                        <Input type="checkbox" name="ctc" id="ctc" checked={item.ctc || false}
                               onChange={this.handleCheckboxChange} />
                    </FormGroup>
                    <FormGroup>
                        <Label for="ffbbUniqueId">FFBB code</Label>
                        <Input type="text" name="ffbbUniqueId" id="ffbbUniqueId" value={item.ffbbUniqueId || ''}
                               onChange={this.handleChange} autoComplete="ffbbUniqueId"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="excelReference">Ref excel</Label>
                        <Input type="text" name="excelReference" id="excelReference" value={item.excelReference || ''}
                               onChange={this.handleChange} autoComplete="excelReference"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="excelReferenceCtc">Ref excel CTC</Label>
                        <Input type="text" name="excelReferenceCtc" id="excelReferenceCtc" value={item.excelReferenceCtc || ''}
                               onChange={this.handleChange} autoComplete="excelReferenceCtc"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="refereeReplacmentLabel">Label planning</Label>
                        <Input type="text" name="refereeReplacmentLabel" id="refereeReplacmentLabel" value={item.refereeReplacmentLabel || ''}
                               onChange={this.handleChange} autoComplete="refereeReplacmentLabel"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="hasOfficialReferee">Avec officiel</Label>
                        <Input type="checkbox" name="hasOfficialReferee" id="hasOfficialReferee" checked={item.hasOfficialReferee || ''}
                               onChange={this.handleCheckboxChange} autoComplete="hasOfficialReferee"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to={this.state.cancelLink}>Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(EquipeEdit);