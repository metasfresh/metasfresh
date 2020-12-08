/**
 *  InlineTabWrapper - component
 *
 *  This is the component that will render when a widget of type 'inlineTab' is present in the layout
 *  - it contains the rows of the inlineTab plus the form that would allow addition of new entries (rows)
 *  - rows are rendered using the <InlineTab> component
 *  - the form for adding a new row is rendered by the SubSections
 *    + the functionality is similar to Add New under the tabs
 *    + the difference is that the  data is disconnected from the main structure
 *    + the data is stored in the Redux Store under the windowHandler.inlineTab.wrapperData path
 *  - actions are found in: src/action/InlineTabActions
 *  - wrapperData index is formed using ${windowId}_${tabId}_${docId} pattern
 *  - adding a new record calls the createWindow action with param NEW for row. From here you will get the rowId on the newly created entry
 *  - data sync with the redux store is done via fetchInlineTabWrapperData action
 *  - inlineTab items keys are formed using ${windowId}_${tabId}_${rowId} pattern
 */
import React, { PureComponent } from 'react';
import InlineTab from './InlineTab';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { createWindow } from '../../actions/WindowActions';
import {
  fetchInlineTabWrapperData,
  setInlineTabAddNew,
} from '../../actions/InlineTabActions';
import SectionGroup from '../SectionGroup';
import counterpart from 'counterpart';

class InlineTabWrapper extends PureComponent {
  constructor(props) {
    super(props);
    this.updateTable(); // this is getting table rows
  }

  /**
   * @method updateTable
   * @summary does a refresh of the table data
   */
  updateTable = () => {
    const query = '';
    const {
      inlineTab: { windowId, tabId },
      dataId: docId,
      fetchInlineTabWrapperData,
    } = this.props;
    fetchInlineTabWrapperData({ tabId, windowId, docId, query });
  };

  /**
   * @method showAddNewForm
   * @summary creates a new Window, the data from here are used for rendering the SectionGroup and also from here we get the rowId
   *          a thing to note here is the `disconnected` flag which specifies the type of Widget, this serves the logic in the WindowActions
   */
  showAddNewForm = () => {
    const {
      createWindow,
      inlineTab: { windowId, tabId },
      dataId: docId,
    } = this.props;
    createWindow({
      windowId,
      docId,
      tabId,
      rowId: 'NEW',
      isModal: true,
      isAdvanced: false,
      disconnected: 'inlineTab',
    });
  };

  /**
   * @method handleFormClose
   * @summary - actions triggered on closing the form, here we toggle the form visibility using the setInlineTabAddNew action
   */
  handleFormClose = () => {
    const { setInlineTabAddNew } = this.props;
    setInlineTabAddNew({ visible: false });
    this.updateTable();
  };

  render() {
    const {
      widgetData,
      tabData,
      addNewFormVisible,
      addNewData,
      rowId,
      inlineTab: { tabId },
      dataId,
    } = this.props;
    const { caption } = widgetData;
    if (!tabData) return false;

    return (
      <div className="inline-tab-wrapper">
        <span>{caption}</span>
        {/* InlineTab Row Items */}
        {tabData &&
          tabData.map((tabItem, index) => (
            <InlineTab
              key={`${index}_${tabItem.rowId}`}
              parent={this.props}
              updateTable={this.updateTable}
              {...tabItem}
            />
          ))}
        <div className="clearfix" />
        {/* Add content wrapper */}
        <div>
          {/* Button */}
          {!addNewFormVisible && (
            <div>
              <button
                className="btn btn-meta-outline-secondary btn-distance btn-sm"
                onClick={this.showAddNewForm}
              >
                {counterpart.translate('window.addNew.caption')}
              </button>
              <div className="clearfix" />
            </div>
          )}
          {/* Actual form */}
          {addNewFormVisible && rowId && addNewData && (
            <div className="inline-tab-active">
              <div className="inline-tab-content">
                <div>
                  <div className="inlinetab-form-header">
                    {counterpart.translate('window.addNew.caption')}
                  </div>
                  <div className="inlinetab-close">
                    <i
                      className="meta-icon-close-alt"
                      onClick={this.handleFormClose}
                    />
                  </div>
                </div>
                <div className="clearfix" />
                <div className="inline-tab-separator" />
                {/* Actual content */}
                <SectionGroup
                  data={addNewData.data}
                  dataId={dataId}
                  layout={addNewData.layout}
                  modal={true}
                  tabId={tabId}
                  rowId={rowId}
                  isModal={true}
                  tabsInfo={null}
                  disconnected={`inlineTab`} // This has to match the windowHandler.inlineTab path in the redux store
                />
              </div>
            </div>
          )}
        </div>
      </div>
    );
  }
}

InlineTabWrapper.propTypes = {
  caption: PropTypes.string.isRequired,
  inlineTab: PropTypes.object.isRequired,
  dataId: PropTypes.string.isRequired,
  createWindow: PropTypes.func.isRequired,
  widgetData: PropTypes.array,
  fetchInlineTabWrapperData: PropTypes.func.isRequired,
  tabData: PropTypes.array,
  rowId: PropTypes.any,
  addNewData: PropTypes.any,
  addNewFormVisible: PropTypes.bool,
  setInlineTabAddNew: PropTypes.func.isRequired,
};

/**
 * @summary - here we do the actual mapping of the redux store props to the local props we need for rendering the elements
 * @param {*} state - redux state
 * @param {*} props - local props
 */
const mapStateToProps = (state, props) => {
  const {
    inlineTab: { windowId, tabId },
    dataId: docId,
  } = props;

  const {
    windowHandler: { inlineTab },
  } = state;
  const selector = `${windowId}_${tabId}_${docId}`;
  const tabData = inlineTab.wrapperData[selector]
    ? inlineTab.wrapperData[selector]
    : null;
  const {
    addNew: { visible: addNewFormVisible, rowId },
  } = inlineTab;
  const addNewData = inlineTab[`${windowId}_${tabId}_${rowId}`];

  return {
    tabData,
    addNewFormVisible,
    addNewData,
    rowId,
  };
};

export default connect(
  mapStateToProps,
  {
    fetchInlineTabWrapperData,
    createWindow,
    setInlineTabAddNew,
  }
)(InlineTabWrapper);
