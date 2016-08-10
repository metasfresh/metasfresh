import React, { Component } from 'react';
import { Link } from 'react-router';

import Datetime from 'react-datetime';
import Dropdown from '../components/app/Dropdown';
import LookupDropdown from '../components/salesOrder/LookupDropdown';

export default class UiElements extends Component {
    render() {
        return (
            <div className="container">
                <h1>UI elements</h1>

                <div className="panel panel-bordered panel-spaced panel-primary panel-distance">
                    <h4>Text</h4>
                    <div className="row">
                        <div className="col-xs-3">
                            <h6>Primary</h6>
                            <p>
                                <div className="input-primary">
                                    <input type="text" className="input-field" />
                                </div>
                            </p>
                        </div>
                        <div className="col-xs-3">
                            <h6>Secondary</h6>
                            <p>
                                <div className="input-secondary">
                                    <input type="text" className="input-field" />
                                </div>
                            </p>
                        </div>
                    </div>
                </div>

                <div className="panel panel-bordered panel-spaced panel-primary panel-distance">
                    <h4>LongText</h4>
                    <div className="row">
                        <div className="col-xs-3">
                            <h6>Primary</h6>
                            <p>
                                <div className="input-primary input-block">
                                    <textarea className="input-field" />
                                </div>
                            </p>
                        </div>
                        <div className="col-xs-3">
                            <h6>Secondary</h6>
                            <p>
                                <div className="input-secondary input-block">
                                    <textarea className="input-field" />
                                </div>
                            </p>
                        </div>
                    </div>
                </div>

                <div className="panel panel-bordered panel-spaced panel-primary panel-distance">
                    <h4>Numeric</h4>
                    <div className="row">
                        <div className="col-xs-3">
                            <h6>Primary</h6>
                            <div>
                                <p className="m-t-1">Integer</p>
                                <div className="input-primary">
                                    <input type="number" className="input-field" min="0" step="1" />
                                </div>
                                <p className="m-t-1">Number</p>
                                <div className="input-primary">
                                    <input type="number" className="input-field" />
                                </div>
                                <p className="m-t-1">Amount</p>
                                <div className="input-primary">
                                    <input type="number" className="input-field" min="0" step="1" />
                                </div>
                                <p className="m-t-1">Quantity</p>
                                <div className="input-primary">
                                    <input type="number" className="input-field" min="0" step="1" />
                                </div>
                                <p className="m-t-1">CostPrice</p>
                                <div className="input-primary">
                                    <input type="number" className="input-field" />
                                </div>
                            </div>
                        </div>
                        <div className="col-xs-3">
                            <h6>Secondary</h6>
                            <div>
                                <p className="m-t-1">Integer</p>
                                <div className="input-secondary">
                                    <input type="number" className="input-field" min="0" step="1" />
                                </div>
                                <p className="m-t-1">Number</p>
                                <div className="input-secondary">
                                    <input type="number" className="input-field" />
                                </div>
                                <p className="m-t-1">Amount</p>
                                <div className="input-secondary">
                                    <input type="number" className="input-field" min="0" step="1" />
                                </div>
                                <p className="m-t-1">Quantity</p>
                                <div className="input-secondary">
                                    <input type="number" className="input-field" min="0" step="1" />
                                </div>
                                <p className="m-t-1">CostPrice</p>
                                <div className="input-secondary">
                                    <input type="number" className="input-field" />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="panel panel-bordered panel-spaced panel-primary panel-distance">
                    <h4>List</h4>
                    <div className="row">
                        <div className="col-xs-3">
                            <h6>Primary</h6>
                            <Dropdown
                                options={['Option1','Option2','Option3']}
                                defaultValue="(none)"
                                rank="primary"
                            />
                        </div>
                        <div className="col-xs-3">
                            <h6>Secondary</h6>
                            <Dropdown
                                options={['Option1','Option2','Option3']}
                                defaultValue="(none)"
                                rank="secondary"
                            />
                        </div>
                    </div>
                </div>

                <div className="panel panel-bordered panel-spaced panel-primary panel-distance">
                    <h4>Lookup</h4>
                    <div className="row">
                        <div className="col-xs-3">
                            <h6>Primary</h6>
                            <LookupDropdown
                                rank="primary"
                                key="dropdown"
                                recent={[{id: 1, n: 'Jazzy'}]}
                                properties={["C_BPartner_ID", "C_BPartner_Location_ID"]}
                            />
                        </div>
                        <div className="col-xs-3">
                            <h6>Secondary</h6>
                            <LookupDropdown
                                rank="secondary"
                                key="dropdown"
                                recent={[{id: 1, n: 'Jazzy'}]}
                                properties={["C_BPartner_ID", "C_BPartner_Location_ID"]}
                            />
                        </div>
                    </div>
                </div>

                <div className="panel panel-bordered panel-spaced panel-primary panel-distance">
                    <h4>DateTime</h4>
                    <div className="row">
                        <div className="col-xs-3">
                            <h6>Primary</h6>
                            <div>
                                <p className="m-t-1">DateTime</p>
                                <div className="input-primary input-block input-icon-container">
                                    <Datetime
                                        timeFormat={true}
                                        dateFormat={true}
                                        locale="de"
                                        inputProps={{placeholder: "(none)"}}
                                    />
                                    <i className="meta-icon-calendar input-icon-right" />
                                </div>
                                <p className="m-t-1">Date</p>
                                <div className="input-primary input-block input-icon-container">
                                    <Datetime
                                        timeFormat={false}
                                        dateFormat={true}
                                        locale="de"
                                        inputProps={{placeholder: "(none)"}}
                                    />
                                    <i className="meta-icon-calendar input-icon-right" />
                                </div>
                                <p className="m-t-1">Time</p>
                                <div className="input-primary input-block input-icon-container">
                                    <Datetime
                                        timeFormat={true}
                                        dateFormat={false}
                                        locale="de"
                                        inputProps={{placeholder: "(none)"}}
                                    />
                                    <i className="meta-icon-calendar input-icon-right"></i>
                                </div>
                            </div>
                        </div>
                        <div className="col-xs-3">
                            <h6>Secondary</h6>
                            <div>
                                <p className="m-t-1">DateTime</p>
                                <div className="input-secondary input-block input-icon-container">
                                    <Datetime
                                        timeFormat={true}
                                        dateFormat={true}
                                        locale="de"
                                        inputProps={{placeholder: "(none)"}}
                                    />
                                    <i className="meta-icon-calendar input-icon-right"></i>
                                </div>
                                <p className="m-t-1">Date</p>
                                <div className="input-secondary input-block input-icon-container">
                                    <Datetime
                                        timeFormat={false}
                                        dateFormat={true}
                                        locale="de"
                                        inputProps={{placeholder: "(none)"}}
                                    />
                                    <i className="meta-icon-calendar input-icon-right"></i>
                                </div>
                                <p className="m-t-1">Time</p>
                                <div className="input-secondary input-block input-icon-container">
                                    <Datetime
                                        timeFormat={true}
                                        dateFormat={false}
                                        locale="de"
                                        inputProps={{placeholder: "(none)"}}
                                    />
                                    <i className="meta-icon-calendar input-icon-right"></i>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>


            </div>
        );
    }
}
