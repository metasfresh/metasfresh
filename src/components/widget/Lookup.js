import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';
import update from 'react-addons-update';

import {
    autocompleteRequest,
    dropdownRequest,
} from '../../actions/AppActions';

class Lookup extends Component {
    constructor(props) {
        super(props);
        this.state = {
            query: "",
            list: [],
            isInputEmpty: true,
            selected: null,
            model: null,
            property: "",
            properties: {},
            loading: false
        }
    }

    componentDidMount() {
        const {defaultValue} = this.props;
        if(defaultValue){
            this.handleSelect(this.props.defaultValue);
        }
    }

    handleSelect = (select) => {
        const {
            dispatch,
            properties,
            onChange,
            dataId,
            fields
        } = this.props;

        // removing selection
        this.setState(
            Object.assign({}, this.state, {
                selected: null
            })
        );

        // dividing fields
        let propertiesCopy = this.getItemsByProperty(properties, "source", "list");
        let mainProperty = this.getItemsByProperty(properties, "source", "lookup");
        // handling selection when main is not set or set.
        if(this.state.property === ""){
            console.log("MAIN FIELD SET");
            this.inputSearch.value = select && select[Object.keys(select)[0]];

            onChange(mainProperty[0].field, select).then(()=>{

                // call for more properties
                let batchArray = [];
                if(propertiesCopy.length > 0){

                    let batch = new Promise((resolve, reject) => {
                        propertiesCopy.map((item) => {
                            dispatch(dropdownRequest(143, item.field, dataId)).then((response)=>{

                                this.setState(update(this.state, {
                                    properties: {
                                        [item.field]: {$set: response.data}
                                    }
                                }), () => {
                                    batchArray.push('0');

                                    if(batchArray.length === propertiesCopy.length){
                                        resolve();
                                    }
                                });

                            });
                        });
                    });

                    batch.then(()=>{
                        this.setState(Object.assign({}, this.state, {
                            model: select
                        }), () => {
                            this.generatingPropsSelection();
                        });
                    });

                }else{
                    this.handleBlur();
                }
            })


        } else {
            console.log("PROPERTY SET");

            this.setState(
                update(this.state, {
                    properties: {
                        [this.state.property]: {$set: [select]}
                    }
            }), () => {
                this.generatingPropsSelection();
            });

            this.handleBlur();
        }
    }

    generatingPropsSelection = () => {
        const {dispatch, onChange} = this.props;

        // Chcecking properties model if there is some
        // unselected properties and handling further
        // selection
        const modelProps = this.state.properties;
        const modelPropsKeys = Object.keys(modelProps);
        //iteration over rest of unselected props
        for(let i=0; i < modelPropsKeys.length; i++){
            onChange(modelPropsKeys[i], modelProps[modelPropsKeys[i]][0]);

            if(modelProps[modelPropsKeys[i]].length === 1){
                // Selecting props that have no choice
                onChange(modelPropsKeys[i], modelProps[modelPropsKeys[i]][0]);
            }else if(modelProps[modelPropsKeys[i]].length > 1){
                // Generating list of props choice
                this.setState(Object.assign({}, this.state, {
                    list: modelProps[modelPropsKeys[i]],
                    property: modelPropsKeys[i]
                }));

                break;

            }else{
                this.handleBlur();
            }
        }

    }

    handleBlur = () => {
        console.log("BLUR")

        this.dropdown.classList.remove("input-dropdown-focused");
        // this.setState(Object.assign({}, this.state, {
        //     property: ""
        // }))
    }

    handleFocus = (e) => {
        console.log("FOCUS")
        const {dispatch} = this.props;
        e.preventDefault();
        this.setState(Object.assign({}, this.state, {
            selected: null
        }));
        if(this.inputSearch.value !== this.state.query){
            this.handleChange();
        }
        // if(this.inputSearch.value === ""){
        //     this.setState(Object.assign({}, this.state, {
        //         property: "", list: recent
        //     }));
        // }
        this.dropdown.classList.add("input-dropdown-focused");
    }

    handleChange = () => {
        console.log("CHANGE")

        const {dispatch, recent, windowType, properties, dataId} = this.props;
        this.inputSearchRest.innerHTML = "";
        this.dropdown.classList.add("input-dropdown-focused");
        // this.setState(Object.assign({}, this.state, {
        //     selected: null,
        //     property: ""
        // }));

        const lookupProps = this.getItemsByProperty(properties, "source", "lookup")[0];

        if(this.inputSearch.value != ""){
            this.setState(Object.assign({}, this.state, {
                isInputEmpty: false,
                loading: true,
                query: this.inputSearch.value
            }));

            dispatch(autocompleteRequest(windowType, lookupProps.field, this.inputSearch.value, dataId))
                .then((response)=>{
                    this.setState(Object.assign({}, this.state, {
                        list: response.data,
                        loading: false
                    }));
                })
        }else{
            this.setState(Object.assign({}, this.state, {
                isInputEmpty: true,
                list: recent
            }));
        }
    }

    handleClear = (e) => {
        console.log("CLEAR")

        const {onChange} = this.props;
        e.preventDefault();
        this.inputSearch.value = "";
        this.handleChange();
        this.handleSelect(null);
    }

    handleKeyDown = (e) => {
        const {dispatch} = this.props;
        switch(e.key){
            case "ArrowDown":
                e.preventDefault();
                this.navigate();
                break;
            case "ArrowUp":
                e.preventDefault();
                this.navigate(true);
                break;
            case "ArrowLeft":
                e.preventDefault();
                this.handleChange();
                break;
            case "Enter":
                e.preventDefault();
                if(this.state.selected != null){
                    this.handleSelect(this.state.list[this.state.selected]);
                }
                break;
            case "Escape":
                e.preventDefault();
                this.handleBlur();
                break;
        }
    }

    navigate = (reverse) => {
        if(this.state.selected != null){
            const selectTarget = this.state.selected + (reverse ? (-1) : (1));
            if (typeof this.state.list[selectTarget] != "undefined") {
                this.setState(Object.assign({}, this.state, {
                    selected: selectTarget}));
            }
        }else if(typeof this.state.list[0] != "undefined"){
            this.setState(Object.assign({}, this.state, {
                selected: 0}))
        }
    }
    getItemsByProperty = (arr, prop, value) => {
        let ret = [];
        arr.map((item, index) => {
            if(item[prop] === value){
                ret.push(item);
            }
        });
        return ret;
    }

    getDropdownComponent = (index, item) => {
        const name = item[Object.keys(item)[0]];
        const key = Object.keys(item)[0];
        return (
            <div
                key={key}
                className={"input-dropdown-list-option " + (this.state.selected == key ? 'input-dropdown-list-option-key-on' : "") }
                onClick={() => this.handleSelect(item)}
            >
                <p className="input-dropdown-item-title">{name}</p>
            </div>
        )
    }

    renderLookup = () => {
        return this.state.list.map((item, index) => this.getDropdownComponent(index, item) );
    }

    render() {
        const {rank, readonly, properties} = this.props;
        return (
            <div
                onKeyDown={this.handleKeyDown}
                tabIndex="0"
                onFocus={()=>this.inputSearch.focus()}
                ref={(c) => this.dropdown = c}
                onBlur={this.handleBlur}
                className={"input-dropdown-container"}
            >
                <div className={"input-dropdown input-block input-" + (rank ? rank : "primary")}>
                    <div className="input-editable">
                        <input
                            type="text"
                            className="input-field font-weight-bold"
                            onFocus={this.handleFocus}
                            onChange={this.handleChange}
                            ref={(c) => this.inputSearch = c}
                            placeholder="(none)"
                            disabled={readonly}
                        />
                    </div>
                    <div ref={c => this.inputSearchRest = c} className="input-rest">

                    </div>

                    {this.state.isInputEmpty ?
                        <div className="input-icon input-icon-lg">
                            <i className="meta-icon-preview" />
                        </div> :
                        <div className="input-icon input-icon-lg" tabIndex="0">
                            <i onClick={this.handleClear} className="meta-icon-close-alt"/>
                        </div>
                    }
                </div>
                <div className="clearfix" />
                <div className="input-dropdown-list">
                    <div className="input-dropdown-list-header">
                        {this.state.loading === false && (
                            (this.state.list.length > 0 ) ?
                                (this.state.query.length !== 0 ? "Are you looking for..." : "Recent lookups") :
                                "There's no matching items."
                            )
                        }
                        {(this.state.loading && this.state.list.length === 0) && (
                            <div className="input-dropdown-list-header">
                                <ReactCSSTransitionGroup transitionName="rotate" transitionEnterTimeout={1000} transitionLeaveTimeout={1000}>
                                    <div className="rotate icon-rotate">
                                        <i className="meta-icon-settings"/>
                                    </div>
                                </ReactCSSTransitionGroup>
                            </div>
                        )}
                    </div>
                    <div ref={(c) => this.items = c}>
                        {this.renderLookup()}
                    </div>
                </div>
            </div>
        )
    }
}


Lookup.propTypes = {
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    return {
    }
}

Lookup = connect(mapStateToProps)(Lookup)

export default Lookup
