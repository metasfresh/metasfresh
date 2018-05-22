import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';

import keymap from '../../shortcuts/keymap';
import Tooltips from '../tooltips/Tooltips';
import TableQuickInput from './TableQuickInput';

class TableFilter extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isTooltipShow: false,
    };
  }

  toggleTooltip = (key = null) => {
    this.setState({
      isTooltipShow: key,
    });
  };

  render() {
    const {
      openModal,
      toggleFullScreen,
      fullScreen,
      docType,
      docId,
      tabId,
      isBatchEntry,
      onBatchEntryToggle,
      supportQuickInput,
      allowCreateNew,
      modalVisible,
    } = this.props;

    const { isTooltipShow } = this.state;

    const tabIndex = fullScreen || modalVisible ? -1 : this.props.tabIndex;

    return (
      <div className="form-flex-align table-filter-line">
        <div className="form-flex-align">
          <div>
            {!isBatchEntry &&
              allowCreateNew && (
                <button
                  className="btn btn-meta-outline-secondary btn-distance btn-sm"
                  onClick={openModal}
                  tabIndex={tabIndex}
                >
                  {counterpart.translate('window.addNew.caption')}
                </button>
              )}
            {supportQuickInput &&
              !fullScreen &&
              allowCreateNew && (
                <button
                  className="btn btn-meta-outline-secondary btn-distance btn-sm"
                  onClick={onBatchEntryToggle}
                  onMouseEnter={() =>
                    this.toggleTooltip(keymap.TOGGLE_QUICK_INPUT)
                  }
                  onMouseLeave={this.toggleTooltip}
                  tabIndex={tabIndex}
                >
                  {isBatchEntry
                    ? counterpart.translate('window.batchEntryClose.caption')
                    : counterpart.translate('window.batchEntry.caption')}
                  {isTooltipShow === keymap.TOGGLE_QUICK_INPUT && (
                    <Tooltips
                      name={keymap.TOGGLE_QUICK_INPUT}
                      action={
                        isBatchEntry
                          ? counterpart.translate(
                              'window.batchEntryClose.caption'
                            )
                          : counterpart.translate('window.batchEntry.caption')
                      }
                      type={''}
                    />
                  )}
                </button>
              )}
          </div>
          {supportQuickInput &&
            (isBatchEntry || fullScreen) &&
            allowCreateNew && (
              <TableQuickInput
                onCloseBatchEntry={onBatchEntryToggle}
                docType={docType}
                docId={docId}
                tabId={tabId}
              />
            )}
        </div>

        {
          <button
            className="btn-icon btn-meta-outline-secondary pointer"
            onClick={() => toggleFullScreen(!fullScreen)}
            onMouseEnter={() => this.toggleTooltip(keymap.TOGGLE_EXPAND)}
            onMouseLeave={this.toggleTooltip}
            tabIndex="-1"
          >
            {fullScreen ? (
              <i className="meta-icon-collapse" />
            ) : (
              <i className="meta-icon-fullscreen" />
            )}

            {isTooltipShow === keymap.TOGGLE_EXPAND && (
              <Tooltips
                name={keymap.TOGGLE_EXPAND}
                action={
                  fullScreen
                    ? counterpart.translate('window.table.collapse')
                    : counterpart.translate('window.table.expand')
                }
                type={''}
              />
            )}
          </button>
        }
      </div>
    );
  }
}

TableFilter.propTypes = {
  dispatch: PropTypes.func.isRequired,
  tabIndex: PropTypes.number.isRequired,
  modalVisible: PropTypes.bool.isRequired,
};

export default connect(state => ({
  modalVisible: state.windowHandler.modal.visible,
}))(TableFilter);
