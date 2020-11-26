import React, { PureComponent } from 'react';
import InlineTab from './InlineTab';
import PropTypes from 'prop-types';
import { fetchTab } from '../../actions/WindowActions';
import { connect } from 'react-redux';

class InlineTabWrapper extends PureComponent {
  constructor(props) {
    super(props);

    this.state = { addNewFormVisible: false };

    const query = '';
    const {
      inlineTab: { windowId, tabId },
      dataId: docId,
      fetchTab,
    } = props;
    fetchTab({ tabId, windowId, docId, query }).then((tabData) => {
      this.tabData = tabData;
    });
  }

  showAddNewForm = () => this.setState({ addNewFormVisible: true });

  handleFormClose = () => this.setState({ addNewFormVisible: false });

  render() {
    if (!this.tabData) return false;
    const { caption } = this.props;
    const { addNewFormVisible } = this.state;
    return (
      <div className="inline-tab-wrapper">
        <span>{caption}</span>
        {this.tabData &&
          this.tabData.map((tabItem, index) => (
            <InlineTab key={`${index}_${tabItem.rowId}`} {...tabItem} />
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
};

export default connect(
  null,
  {
    fetchTab,
  }
)(InlineTabWrapper);
