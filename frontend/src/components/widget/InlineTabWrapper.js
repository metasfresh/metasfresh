import React, { PureComponent } from 'react';
import InlineTab from './InlineTab';
import PropTypes from 'prop-types';
import { fetchTab } from '../../actions/WindowActions';
import { connect } from 'react-redux';
import {
  createWindow,
  setInlineTabWrapperData,
} from '../../actions/WindowActions';

class InlineTabWrapper extends PureComponent {
  constructor(props) {
    super(props);

    this.state = { addNewFormVisible: false };
    this.updateTable(); // this is getting table rows
  }

  updateTable = () => {
    const query = '';
    const {
      inlineTab: { windowId, tabId },
      dataId: docId,
      fetchTab,
      setInlineTabWrapperData,
    } = this.props;
    fetchTab({ tabId, windowId, docId, query }).then((tabData) => {
      setInlineTabWrapperData({
        inlineTabWrapperId: `${windowId}_${tabId}_${docId}`,
        data: tabData,
      });
    });
  };

  showAddNewForm = () => {
    const {
      createWindow,
      inlineTab: { windowId, tabId },
      dataId: docId,
    } = this.props;
    createWindow(windowId, docId, tabId, 'NEW', true, true);
    this.setState({ addNewFormVisible: true });
  };

  handleFormClose = () => this.setState({ addNewFormVisible: false });

  render() {
    const { addNewFormVisible } = this.state;
    const { widgetData, tabData } = this.props;
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
                + Add new
              </button>
              <div className="clearfix" />
            </div>
          )}
          {/* Actual content */}
          {addNewFormVisible && (
            <div className="inline-tab-active">
              <div className="inline-tab-content">
                <div>
                  <div className="inlinetab-form-header">Add new record</div>
                  <div className="inlinetab-close">
                    <i
                      className="meta-icon-close-alt"
                      onClick={this.handleFormClose}
                    />
                  </div>
                </div>
                <div className="clearfix" />
                <div className="inline-tab-separator" />
                Actual content
                {/* <Window
                  data={data}
                  dataId={docId}
                  layout={this.props.inlineTab}
                  modal
                  tabId={tabId}
                  rowId={rowId}
                  isModal
                  tabsInfo={null}
                /> */}
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
  fetchTab: PropTypes.func.isRequired,
  createWindow: PropTypes.func.isRequired,
  widgetData: PropTypes.array,
  setInlineTabWrapperData: PropTypes.func.isRequired,
  tabData: PropTypes.array,
};

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
  return {
    tabData,
  };
};

export default connect(
  mapStateToProps,
  {
    fetchTab,
    createWindow,
    setInlineTabWrapperData,
  }
)(InlineTabWrapper);
