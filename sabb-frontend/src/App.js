import React, { Component } from 'react'
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import AssociationList from './view/AssociationList'
import AssociationEdit from './view/AssociationEdit'
import Home from './view/Home'

class App extends Component {
  render() {
    return (
        <Router>
            <Switch>
                <Route path='/' exact={true} component={Home}/>
                <Route path='/associations' exact={true} component={AssociationList}/>
                <Route path='/associations/:id' component={AssociationEdit}/>
            </Switch>
        </Router>
    )
  }
}

export default App