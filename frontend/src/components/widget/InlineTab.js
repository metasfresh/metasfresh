import React, { PureComponent } from 'react';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import Window from '../../components/Window';
import { getLayout, getData } from '../../api/view';
import { setInlineTabLayoutAndData } from '../../actions/WindowActions';

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
    const {
      windowId,
      id: docId,
      tabId,
      setInlineTabLayoutAndData,
      rowId,
    } = this.props;

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
                tabId,
                fetchAdvancedFields: false,
              }).then(({ data: respFields }) => {
                const { result } = respFields;
                const wantedData = result.filter(
                  (item) => item.rowId === rowId
                );
                setInlineTabLayoutAndData({
                  inlineTabId: `${windowId}_${tabId}_${rowId}`,
                  data: { layout: layoutData, data: wantedData[0] },
                });
              });
            }
          );
      }
    );
  };

  render() {
    const { id: docId, rowId, tabId, layout, data, fieldsByName } = this.props;
    const { isOpen } = this.state;
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
        </div>

        {/* Content */}
        {isOpen && (
          <div className="inline-tab-active inline-tab-offset-top">
            <div className="inline-tab-content">
              {layout && data && (
                <Window
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
  setInlineTabLayoutAndData: PropTypes.func.isRequired,
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
    setInlineTabLayoutAndData,
  }
)(InlineTab);
