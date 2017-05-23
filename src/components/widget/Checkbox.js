import React, { Component } from 'react';

class Checkbox extends Component {
    constructor(props) {
        super(props);
    }

    handleClear = () => {
        const {handlePatch, widgetField, id} = this.props;
        handlePatch(widgetField, '', id);
    }

    render() {
        const {
            widgetData, disabled, fullScreen, tabIndex, handlePatch,
            widgetField, id, filterWidget
        } = this.props;

        return (
            <div>
                <label
                    className={
                        'input-checkbox ' +
                        (widgetData[0].readonly || disabled ?
                            'input-disabled ' : '')
                    }
                    tabIndex={fullScreen ? -1 : tabIndex}
                    ref={c => this.rawWidget = c}
                    onKeyDown={e => {
                        if(e.key === ' '){
                            e.preventDefault();
                            this.rawWidget && this.rawWidget.click();
                        }
                    }}
                >
                    <input
                        ref={c => this.rawWidget = c}
                        type="checkbox"
                        checked={widgetData[0].value}
                        disabled={widgetData[0].readonly || disabled}
                        onChange={(e) => handlePatch(
                            widgetField, e.target.checked, id
                        )}
                        tabIndex="-1"
                    />
                    <div className="input-checkbox-tick" />
                </label>
                {(filterWidget && !disabled && !widgetData[0].readonly && 
                    !widgetData[0].value !== '') ?
                    <span className="input-icon-checkbox input-icon-lg">
                        <i
                            onClick={this.handleClear}
                            className="meta-icon-close-alt"
                        />
                    </span> : ''
                }
            </div>
        );
    }
}

export default Checkbox;
