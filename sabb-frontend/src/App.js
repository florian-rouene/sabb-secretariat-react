import React, { Component } from 'react'
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import AssociationList from './view/AssociationList'
import AssociationEdit from './view/AssociationEdit'
import CategorieList from './view/CategorieList'
import CategorieEdit from './view/CategorieEdit'
import SubCategorieList from './view/SubCategorieList'
import SubCategorieEdit from './view/SubCategorieEdit'
import EquipeList from './view/EquipeList'
import FfbbEquipeList from './view/FfbbEquipeList'
import EquipeEdit from './view/EquipeEdit'
import MatchList from './view/MatchList'
import OfficialList from './view/OfficialList'
import LicenseeList from './view/LicenseeList'
import LicenseeEdit from './view/LicenseeEdit'
import Home from './view/Home'

class App extends Component {
  render() {
    return (
        <Router>
            <Switch>
                <Route path='/' exact={true} component={Home}/>
                <Route path='/associations' exact={true} component={AssociationList}/>
                <Route path='/associations/:id' component={AssociationEdit}/>
                <Route path='/categories' exact={true} component={CategorieList}/>
                <Route path='/categories/:id' component={CategorieEdit}/>
                <Route path='/sub_categories' exact={true} component={SubCategorieList}/>
                <Route path='/sub_categories/:id' component={SubCategorieEdit}/>
                <Route path='/equipes' exact={true} component={EquipeList}/>
                <Route path='/equipes/:id/:ffbbId/:name/:asso' component={EquipeEdit}/>
                <Route path='/equipes/:id' component={EquipeEdit}/>                
                <Route path='/ffbb_equipes/:ffbbName' exact={true} component={FfbbEquipeList}/>
                <Route path='/matchs' exact={true} component={MatchList}/>
                <Route path='/officials' exact={true} component={OfficialList}/>
                <Route path='/licensees' exact={true} component={LicenseeList}/>
                <Route path='/licensees/:id' exact={true} component={LicenseeEdit}/>
            </Switch>
        </Router>
    )
  }
}

export default App