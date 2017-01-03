import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import TableQuickInput from './TableQuickInput';
import Tooltips from '../tooltips/Tooltips';

class TableFilter extends Component {
    constructor(props) {
        super(props);

        this.state = {
            isTooltipShow: false
        }
    }

    toggleTooltip = () => {
        const {isTooltipShow} = this.state;
        this.setState(Object.assign({}, this.state, {
            isTooltipShow: !isTooltipShow
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
                        >
                            Add new
                        </button>}
                        {!fullScreen && <button
                            className="btn btn-meta-outline-secondary btn-distance btn-sm"
                            onClick={handleBatchEntryToggle}
                        >
                            {isBatchEntry ? "Close batch entry" : "Batch entry"}
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
                    onMouseEnter={this.toggleTooltip}
                    onMouseLeave={this.toggleTooltip}
                >
                    {fullScreen ? <i className="meta-icon-fullscreen"/> : <i className="meta-icon-fullscreen"/>}

                    {isTooltipShow && <Tooltips
                        name={fullScreen ? "Expand" : "Collapse"}
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
