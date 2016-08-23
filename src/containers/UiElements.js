import React, { Component } from 'react';
import { Link } from 'react-router';

import Datetime from 'react-datetime';
import Dropdown from '../components/app/Dropdown';
import Tabs from '../components/app/Tabs';
import LookupDropdown from '../components/app/LookupDropdown';

export default class UiElements extends Component {
    constructor(props) {
        super(props);
        this.state = {

        }
    }
    render() {
        return (
            <div className="container">
                <h1>UI elements</h1>

                <div className="panel panel-bordered panel-spaced panel-primary panel-distance">
                    <h4>Text</h4>
                    <div className="row">
                        <div className="col-xs-3">
                            <h6>Primary</h6>
                            <div className="input-primary">
                                <input type="text" className="input-field" />
                            </div>
                        </div>
                        <div className="col-xs-3">
                            <h6>Secondary</h6>
                            <div className="input-secondary">
                                <input type="text" className="input-field" />
                            </div>
                        </div>
                    </div>
                </div>

                <div className="panel panel-bordered panel-spaced panel-primary panel-distance">
                    <h4>LongText</h4>
                    <div className="row">
                        <div className="col-xs-3">
                            <h6>Primary</h6>
                            <div className="input-primary input-block">
                                <textarea className="input-field" />
                            </div>
                        </div>
                        <div className="col-xs-3">
                            <h6>Secondary</h6>
                            <div className="input-secondary input-block">
                                <textarea className="input-field" />
                            </div>
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

                <div className="panel panel-bordered panel-spaced panel-primary panel-distance">
                    <h4>Switch, YesNo, Label</h4>
                    <div className="row">
                        <div className="col-xs-12">
                            <p className="m-t-1">Switch</p>
                            <label className="input-switch">
                                <input type="checkbox" />
                                <div className="input-slider" />
                            </label>
                            <p className="m-t-1">YesNo</p>
                            <p><small>Normal</small></p>
                            <label className="input-checkbox">
                        		<input type="checkbox" />
                        		<div className="input-checkbox-tick"/>
                        	</label>
                            <p><small>Disabled</small></p>
                            <label className="input-checkbox">
                        		<input type="checkbox" disabled />
                        		<div className="input-checkbox-tick"/>
                        	</label>
                            <p className="m-t-1">Label</p>
                            <div className="tag tag-warning">Open</div>

                        </div>
                    </div>
                </div>

                <Tabs>
                    <div caption="Order Lines" key="1">ord lns 1</div>
                    <div caption="Order Lines 2" key="2">ord lns 2</div>
                </Tabs>
            </div>
        );
    }
}
