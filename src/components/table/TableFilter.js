import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import TableQuickInput from './TableQuickInput';
import Tooltips from '../tooltips/Tooltips';
import keymap from '../../keymap.js';

class TableFilter extends Component {
    constructor(props) {
        super(props);

        this.state = {
            isTooltipShow: false
        }
    }

    toggleTooltip = (key = null) => {
        const {isTooltipShow} = this.state;
        this.setState(Object.assign({}, this.state, {
            isTooltipShow: key
        }));
    }

    render() {
        const {
            openModal, toggleFullScreen, fullScreen, docType, docId, tabId, tabIndex,
            isBatchEntry, handleBatchEntryToggle
        } = this.props;

        const {
            isTooltipShow
        } = this.state;

        return (
            <div className="form-flex-align table-filter-line">
                <div className="form-flex-align">
                    <div>
                        {!isBatchEntry && <button
                            className="btn btn-meta-outline-secondary btn-distance btn-sm"
                            onClick={openModal}
                            tabIndex="-1"
                        >
                            Add new
                        </button>}
                        {!fullScreen && <button
                            className="btn btn-meta-outline-secondary btn-distance btn-sm"
                            onClick={handleBatchEntryToggle}
                            onMouseEnter={() => this.toggleTooltip(keymap.TABLE_CONTEXT.TOGGLE_QUICK_INPUT)}
                            onMouseLeave={this.toggleTooltip}
                            tabIndex="-1"
                        >
                            {isBatchEntry ? 'Close batch entry' : 'Batch entry'}
                            {isTooltipShow === keymap.TABLE_CONTEXT.TOGGLE_QUICK_INPUT && <Tooltips
                                name={keymap.TABLE_CONTEXT.TOGGLE_QUICK_INPUT}
                                action={isBatchEntry ? 'Close batch entry' : 'Batch entry'}
                                type={''}
                            />}
                        </button>}
                    </div>
                    {(isBatchEntry || fullScreen) &&
                        <TableQuickInput
                            docType={docType}
                            docId={docId}
                            tabId={tabId}
                        />
                    }
                </div>

                {<button
                    className="btn-icon btn-meta-outline-secondary pointer"
                    onClick={() => toggleFullScreen(!fullScreen)}
                    onMouseEnter={() => this.toggleTooltip(keymap.TABLE_CONTEXT.TOGGLE_EXPAND)}
                    onMouseLeave={this.toggleTooltip}
                    tabIndex="-1"
                >
                    {fullScreen ? <i className="meta-icon-collapse"/> : <i className="meta-icon-fullscreen"/>}

                    {isTooltipShow === keymap.TABLE_CONTEXT.TOGGLE_EXPAND && <Tooltips
                        name={keymap.TABLE_CONTEXT.TOGGLE_EXPAND}
                        action={fullScreen ? 'Collapse' : 'Expand'}
                        type={''}
                    />}
                </button>}
            </div>
        )
    }
}

TableFilter.propTypes = {
    dispatch: PropTypes.func.isRequired
}

TableFilter = connect()(TableFilter);

export default TableFilter;
