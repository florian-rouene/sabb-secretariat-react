import React, { Component } from 'react'

class Association extends Component {
  state = {
    data: [],
  }

  // Code is invoked after the component is mounted/inserted into the DOM tree.
  componentDidMount() {
    
    fetch('/associations?name=all')
    .then(results => {
        return results.json();
      })
      .then((result) => {
        this.setState({
          data: result,
        })
      })
  }

  render() {
    const { data } = this.state

    const result = data.map((entry, index) => {
      return <li key={entry.id}>{entry.name}</li>
    })

    return <ul>{result}</ul>
  }
}

export default Association