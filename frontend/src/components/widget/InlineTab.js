/**
 *  InlineTab - component
 *
 *  This renders the lines of the main InlineTabWrapper
 *  - has just two local flags for knowing the open/closed state and the rendering state of the prompt (deletion confirmation)
 *  - when the row is clicked it will fetch the data used to feed the SectionGroup widget
 *  - second click on the row it will close the edit mode
 *
 */
import React, { PureComponent, Fragment } from 'react';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import SectionGroup from '../SectionGroup';
import { updateDataValidStatus } from '../../actions/WindowActions';
import {
  getInlineTabLayoutAndData,
  setInlineTabItemProp,
} from '../../actions/InlineTabActions';
import { fieldValueToString } from '../../utils/tableHelpers';
import { deleteRequest } from '../../api';
import Prompt from '../app/Prompt';
import counterpart from 'counterpart';
import { isGermanLanguage } from '../../utils/locale';
class InlineTab extends PureComponent {
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
      isOpen,
    } = this.props;

    getInlineTabLayoutAndData({
      windowId,
      tabId,
      docId,
      rowId,
    });
    this.setProperty({ targetProp: 'isOpen', targetValue: !isOpen });
  };

  /**
   * @method setProperty
   * @summary this updates a property for the inlineTabItem (row) to a given value
   * @param {string} targetProp - the targeted property (i.e. `propmtOpen`)
   * @param {*} targetValue - value of the property can be whaterver you want boolean, string aso.
   */
  setProperty = ({ targetProp, targetValue }) => {
    const { setInlineTabItemProp, windowId, tabId, rowId } = this.props;
    setInlineTabItemProp({
      inlineTabId: `${windowId}_${tabId}_${rowId}`,
      targetProp,
      targetValue,
    });
  };

  /**
   * @method handleDelete
   * @summary this shows the confirm dialog box
   */
  handleDelete = () => {
    this.setProperty({ targetProp: 'promptOpen', targetValue: true });
  };

  /**
   * @method handlePromptCancel
   * @summary closes the confirmation dialog by setting the local state flag to false
   */
  handlePromptCancel = () => {
    this.setProperty({ targetProp: 'promptOpen', targetValue: false });
  };

  /**
   * @method handlePromptDelete
   * @summary - sets the promptOpen flag to `false` value hiding the confirmation dialog and performs a deleteRequest
   *            when the promisse is fulfilled it refreshes the table data
   */
  handlePromptDelete = () => {
    this.setProperty({ targetProp: 'promptOpen', targetValue: false });
    const {
      windowId,
      id: docId,
      tabId,
      rowId,
      updateTable,
      updateDataValidStatus,
    } = this.props;
    deleteRequest('window', windowId, docId, tabId, rowId).then(
      (deleteResponse) => {
        updateTable(true); /** we set the postDeletion to `true` */
        let { validStatus } = deleteResponse.data[0];
        updateDataValidStatus('master', validStatus || { valid: true });
      }
    );
  };

  /**
   * @method formatHeaderDataUsingType
   * @summary function responsible for converting properly the value of the field used further for concatenating the header caption
   */
  formatHeaderDataUsingType = ({ fieldValue, fieldType, precision }) => {
    const { isGerman } = this.props;

    if (fieldValue === null) return;
    return fieldValueToString({ fieldValue, fieldType, precision, isGerman });
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
      fieldsOrder,
      promptOpen,
      isOpen,
      allowDelete,
    } = this.props;
    const valid = validStatus ? validStatus.valid : true;

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
            {fieldsOrder.map((fieldKey, index) => {
              if (fieldsByName[fieldKey]) {
                return (
                  <Fragment key={`${fieldKey}_${index}`}>
                    <span>
                      {this.formatHeaderDataUsingType({
                        fieldValue: fieldsByName[fieldKey].value,
                        fieldType: fieldsByName[fieldKey].widgetType,
                        precision: fieldsByName[fieldKey].precision,
                      })}
                    </span>
                    <span>&nbsp;&nbsp;</span>
                  </Fragment>
                );
              }
            })}
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
                  {allowDelete && (
                    <div className="row">
                      <div className="col-lg-12">
                        <button
                          className="btn btn-meta-outline-secondary btn-sm btn-pull-right"
                          onClick={() => this.handleDelete(rowId)}
                        >
                          {counterpart.translate('window.Delete.caption')}
                        </button>
                        <div className="clearfix" />
                      </div>
                    </div>
                  )}
                  {/* These prompt strings are hardcoded because they need to be provided by the BE */}
                  {promptOpen && (
                    <Prompt
                      title={counterpart.translate('window.Delete.caption')}
                      text={counterpart.translate('window.delete.message')}
                      buttons={{
                        submit: counterpart.translate('window.delete.confirm'),
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
  promptOpen: PropTypes.bool,
  fieldsOrder: PropTypes.array.isRequired,
  updateDataValidStatus: PropTypes.func.isRequired,
  setInlineTabItemProp: PropTypes.func.isRequired,
  isOpen: PropTypes.bool,
  isGerman: PropTypes.bool,
  allowDelete: PropTypes.bool,
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
  const promptOpen = inlineTab[selector]
    ? inlineTab[selector].promptOpen
    : false;
  const isOpen = inlineTab[selector] ? inlineTab[selector].isOpen : false;
  const isGerman = isGermanLanguage(state.appHandler.me.language);

  return {
    layout,
    data,
    promptOpen,
    isOpen,
    isGerman,
  };
};

export default connect(mapStateToProps, {
  getInlineTabLayoutAndData,
  updateDataValidStatus,
  setInlineTabItemProp,
})(InlineTab);
