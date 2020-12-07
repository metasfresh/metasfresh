import React, { PureComponent } from 'react';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import SectionGroup from '../SectionGroup';
import { getInlineTabLayoutAndData } from '../../actions/WindowActions';

class InlineTab extends PureComponent {
  constructor(props) {
    super(props);
    this.state = { isOpen: false };
  }

  toggleOpen = () => {
    const {
      windowId,
      id: docId,
      tabId,
      rowId,
      getInlineTabLayoutAndData,
    } = this.props;

    this.setState(
      (prevState) => {
        return { isOpen: !prevState.isOpen };
      },
      () => {
        this.state.isOpen &&
          getInlineTabLayoutAndData({
            windowId,
            tabId,
            docId,
            rowId,
          });
      }
    );
  };

  render() {
    const {
      id: docId,
      rowId,
      tabId,
      layout,
      data,
      fieldsByName,
      validStatus: { valid },
    } = this.props;
    const { isOpen } = this.state;

    return (
      <div>
        <div
          className={classnames(
            { 'inline-tab': !isOpen },
            { 'inline-tab-active': isOpen },
            { 'form-control-label': true },
            { 'row-not-saved': !valid }
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
        </div>

        {/* Content */}
        {isOpen && (
          <div className="inline-tab-active inline-tab-offset-top">
            <div className="inline-tab-content">
              {layout && data && (
                <SectionGroup
                  data={data}
                  dataId={docId}
                  layout={layout}
                  modal={true}
                  tabId={tabId}
                  rowId={rowId}
                  isModal={true}
                  tabsInfo={null}
                  disconnected={`inlineTab`} // This has to match the windowHandler.inlineTab path in the redux store
                />
              )}
            </div>
          </div>
        )}
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
  layout: PropTypes.any,
  data: PropTypes.any,
  validStatus: PropTypes.object.isRequired,
  getInlineTabLayoutAndData: PropTypes.func.isRequired,
};

const mapStateToProps = (state, props) => {
  const { windowId, tabId, rowId } = props;
  const {
    windowHandler: { inlineTab },
  } = state;
  const selector = `${windowId}_${tabId}_${rowId}`;
  const layout = inlineTab[selector] ? inlineTab[selector].layout : null;
  const data = inlineTab[selector] ? inlineTab[selector].data : null;
  return {
    layout,
    data,
  };
};

export default connect(
  mapStateToProps,
  {
    getInlineTabLayoutAndData,
  }
)(InlineTab);
