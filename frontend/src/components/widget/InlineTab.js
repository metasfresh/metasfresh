import React, { PureComponent } from 'react';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import Window from '../../components/Window';
import { createWindow } from '../../actions/WindowActions';
import { connect } from 'react-redux';
import { getLayout, getData } from '../../api/view';
class InlineTab extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      isOpen: false,
      layout: null,
      data: null,
    };
  }

  toggleOpen = () => {
    const { createWindow, windowId, id: docId, tabId, rowId } = this.props;
    this.setState(
      (prevState) => {
        return { isOpen: !prevState.isOpen };
      },
      () => {
        this.state.isOpen &&
          getLayout('window', windowId, tabId, null, null, false).then(
            ({ data: layoutData }) => {
              getData({
                entity: 'window',
                docType: windowId,
                docId,
                fetchAdvancedFields: false,
              }).then(({ fieldsByName }) =>
                this.setState({ layout: layoutData, data: fieldsByName })
              );
            }
          );
        !this.state.isOpen && this.setState({ layout: null });
      }
    );
  };

  render() {
    const { isOpen, layout, data } = this.state;
    const { fieldsByName, windowId, id: docId, tabId, rowId } = this.props;

    return (
      <div>
        <div
          className={classnames(
            { 'inline-tab': !isOpen },
            { 'inline-tab-active': isOpen },
            { 'form-control-label': true }
          )}
          onClick={this.toggleOpen}
        >
          <div className="pull-left">
            <span className="arrow-pointer" />
          </div>
          {/* Header  */}
          <div className="pull-left offset-left">
            <span>{fieldsByName.Name.value}</span>&nbsp;&nbsp;
            <span>{fieldsByName.Address.value}</span>
          </div>

          {/* Content */}
          {isOpen && (
            <div className="inline-tab-content">
              <div className="inline-tab-separator" />
              {!layout && <span>Loading ...</span>}
              {layout && (
                <Window
                  data={data}
                  dataId={docId}
                  layout={layout}
                  modal
                  tabId={tabId}
                  rowId={rowId}
                  isModal
                  tabsInfo={null}
                />
              )}
            </div>
          )}
        </div>
      </div>
    );
  }
}

InlineTab.propTypes = {
  windowId: PropTypes.string.isRequired,
  id: PropTypes.string.isRequired,
  rowId: PropTypes.string.isRequired,
  tabId: PropTypes.string.isRequired,
  fieldsByName: PropTypes.object,
  createWindow: PropTypes.func.isRequired,
};

export default connect(
  null,
  {
    createWindow,
  }
)(InlineTab);
