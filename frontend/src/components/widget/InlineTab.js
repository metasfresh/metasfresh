/**
 *  InlineTab - component
 *
 *  This renders the lines of the main InlineTabWrapper
 *  - has just two local flags for knowing the open/closed state and the rendering state of the prompt (deletion confirmation)
 *  - when the row is clicked it will fetch the data used to feed the SectionGroup widget
 *  - second click on the row it will close the edit mode
 *
 */
import React, { PureComponent } from 'react';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import SectionGroup from '../SectionGroup';
import { getInlineTabLayoutAndData } from '../../actions/InlineTabActions';
import { deleteRequest } from '../../api';
import Prompt from '../app/Prompt';
import counterpart from 'counterpart';

class InlineTab extends PureComponent {
  constructor(props) {
    super(props);
    this.state = { isOpen: false, promptOpen: false };
  }

  /**
   * @method toggleOpen
   * @summary - toggles the visibility of the edit mode, it gets the data using getInlineTabLayoutAndData action and it feeds the store
   */
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

  /**
   * @method handleDelete
   * @summary this shows the confirm dialog box
   */
  handleDelete = () => this.setState({ promptOpen: true });

  /**
   * @method handlePromptCancel
   * @summary closes the confirmation dialog by setting the local state flag to false
   */
  handlePromptCancel = () => this.setState({ promptOpen: false });

  /**
   * @method handlePromptDelete
   * @summary - sets the promptOpen flag to `false` value hiding the confirmation dialog and performs a deleteRequest
   *            when the promisse is fulfilled it refreshes the table data
   */
  handlePromptDelete = () => {
    this.setState({ promptOpen: false });
    const { windowId, id: docId, tabId, rowId, updateTable } = this.props;
    deleteRequest('window', windowId, docId, tabId, rowId).then(() =>
      updateTable()
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
      validStatus,
    } = this.props;
    const valid = validStatus ? validStatus.valid : true;
    const { isOpen, promptOpen } = this.state;

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
                <div>
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
                  {/* Delete button */}
                  <div className="row">
                    <div className="col-lg-12">
                      <button
                        className="btn btn-meta-outline-secondary btn-sm btn-pull-right"
                        onClick={() => this.handleDelete(rowId)}
                      >
                        Delete
                      </button>
                      <div className="clearfix" />
                    </div>
                  </div>
                  {/* These prompt strings are hardcoded because they need to be provided by the BE */}
                  {promptOpen && (
                    <Prompt
                      title={counterpart.translate('window.Delete.caption')}
                      text={counterpart.translate('window.delete.message')}
                      buttons={{
                        submit: counterpart.translate('window.delete.submit'),
                        cancel: counterpart.translate('window.delete.cancel'),
                      }}
                      onCancelClick={this.handlePromptCancel}
                      selected={rowId}
                      onSubmitClick={this.handlePromptDelete}
                    />
                  )}
                </div>
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
  validStatus: PropTypes.object,
  getInlineTabLayoutAndData: PropTypes.func.isRequired,
  updateTable: PropTypes.func,
};

/**
 * @summary - here we do the actual mapping of the redux store props to the local props we need for rendering the InlineTab
 *            basically we are getting the `data` and the `layout`
 * @param {*} state - redux state
 * @param {*} props - local props
 */
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
