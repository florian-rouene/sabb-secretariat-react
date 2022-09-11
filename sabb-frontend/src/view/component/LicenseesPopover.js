import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Button, Popover, PopoverHeader, PopoverBody, Input } from "reactstrap";

const LicenseesPicker = props => {

  const [licensees, setLicensees] = useState(props.licensees);
  const [officials, setOfficials] = useState({licenseeTable1Id: 0});
  
  
  const filterByTeam = (event) => {
      let selectedValue = Number(event.target.value);
      if (selectedValue !== -1) {
          let filteredOfficial = [...licensees].filter(e => e.teamId === selectedValue);
          setLicensees(filteredOfficial);
      } else {
        setLicensees(props.licensees);
      }
  }

  const handleSubmit = (event) => {
    event.preventDefault();
    fetch('/officials/' + props.match.id, {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(officials),
    });
  }

  const handleLicenceeChange = (event) => {
    const target = event.target;        
    const value = target.value;
    const name = target.name;    
    officials[name] = value;
    setOfficials(officials);
    console.log(officials)
}
 
  return (
    <div>
      <Input type="select" name="teamFilter" onChange={filterByTeam}>
          <option value={-1}></option>
          {                        
              props.equipes.map(equipe => { 
                  return <option value={equipe.id}>{equipe.name}</option>;
              })
          }
      </Input>
      <Input type="select" name="licenseeTable1Id" onChange={handleLicenceeChange}>
          <option value={-1}></option>
          {                        
              licensees.map(licensee => { 
                  return <option value={licensee.id}>{licensee.name}</option>;
              })
          }
      </Input>
      <Button color="primary" type="submit" onClick={handleSubmit}>Save</Button>{' '}
    </div>
  );
};


const LicenseesPopover = props => {
  const [popoverOpen, setPopoverOpen] = useState(false);

  const toggle = () => setPopoverOpen(!popoverOpen);

  return (
    <div
      className="d-flex align-items-center justify-content-center"
    >
      <Button id="Popover1" type="button" color="primary">
        Launch Popover
      </Button>
      <Popover {...props}
        placement="bottom"
        isOpen={popoverOpen}
        target="Popover1"
        toggle={toggle}
      >
        <PopoverHeader>Popover Title</PopoverHeader>
        <PopoverBody>
          <LicenseesPicker {...props}/>
        </PopoverBody>
      </Popover>
    </div>
  );
};

export default LicenseesPopover;