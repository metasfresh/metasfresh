import React, { Component } from 'react';
import { Link } from 'react-router';

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
                                    <input type="text" className="input-field" />
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
                                    <input type="text" className="input-field" />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        );
    }
}
