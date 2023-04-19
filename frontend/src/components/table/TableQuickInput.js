import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import onClickOutside from 'react-onclickoutside';
import {
  completeQuickInput,
  deleteQuickInput,
  fetchQuickInputData,
  fetchQuickInputLayout,
  patchQuickInput,
  updateQuickinputData,
} from '../../actions/TableQuickInputActions';

import WidgetWrapper from '../../containers/WidgetWrapper';

class TableQuickInput extends PureComponent {
  // promise with patching for queuing form submission after patch is done
  patchPromise;
  // widgets refs
  rawWidgets = [];
  rawWidgetsByFieldName = {};
  // local state
  state = { hasFocus: true, isSubmitPending: false };

  componentDidMount() {
    // noinspection JSIgnoredPromiseFromCall
    this.initQuickInput();
  }

  componentWillUnmount() {
    const { deleteQuickInput } = this.props;
    deleteQuickInput();
  }

  componentDidUpdate() {
    // --------------
    // NOTE: don't focus on next mandatory field on each update because,
    // that will skip packing instructions field after user enters product in sales order for example.
    // {{{{{{{{{{{{{
    // const { inProgress } = this.props;
    // const { hasFocus } = this.state;
    //
    // // focus first field with errors
    // if (hasFocus && !inProgress) {
    //   this.focusFirstFieldWithErrors();
    // }
    // }}}}}}}}}}}}}
  }

  focusFirstFieldWithErrors = () => {
    const validationResult = this.validateForm();
    if (validationResult.fieldName) {
      this.focusField(validationResult.fieldName);
    }
  };

  focusField = (fieldName) => {
    const widget = this.rawWidgetsByFieldName[fieldName];
    widget?.focus?.();
  };

  initQuickInput = async () => {
    const {
      windowId,
      docId,
      tabId,
      addNotification,
      fetchQuickInputData,
      fetchQuickInputLayout,
    } = this.props;

    await fetchQuickInputData({
      windowId,
      docId,
      tabId,
    })
      .then(() => this.resetWidgetValues())
      .catch((err) => {
        if (err.response.status === 404) {
          addNotification(
            'Batch entry error',
            'Batch entry is not available.',
            5000,
            'error'
          );
          this.closeBatchEntry();
        }
      });

    await fetchQuickInputLayout({
      windowId,
      docId,
      tabId,
    }).catch(({ response }) => {
      const { error, message } = response.data;

      addNotification(error, message, 5000, 'error');
      // eslint-disable-next-line no-console
      console.error(error);
      this.closeBatchEntry();
    });

    this.focusFirstFieldWithErrors();
  };

  handleChange = (field, value) => {
    const { updateQuickinputData } = this.props;
    const fieldData = {};
    fieldData[field] = { value };

    updateQuickinputData(fieldData);
  };

  handlePatch = (prop, value, callback) => {
    const { windowId, docId, tabId, patchQuickInput } = this.props;

    this.patchPromise = new Promise((resolve) => {
      patchQuickInput({ windowId, docId, tabId, prop, value }).then(() => {
        if (callback) {
          callback();
        }
        resolve();
      });
    });
  };

  onSubmit = (e) => {
    if (this.state.isSubmitPending) {
      return;
    }

    const { windowId, docId, tabId, quickInputId, addNotification } =
      this.props;

    e.preventDefault();

    // blur to make sure the field value is committed, events are fired, etc
    const activeElement = document.activeElement;
    activeElement.blur();

    const validationResult = this.validateForm();
    if (validationResult.error) {
      // Focus the last active element, to allow user continuing typing.
      activeElement.focus();

      return addNotification('Error', validationResult.error, 5000, 'error');
    }

    this.setState({ isSubmitPending: true });

    return this.patchPromise
      .then(() => completeQuickInput({ windowId, docId, tabId, quickInputId }))
      .then(this.initQuickInput)
      .finally(() => this.setState({ isSubmitPending: false }));
  };

  /** Validates form data returning first error */
  validateForm = () => {
    const { data } = this.props;

    if (data) {
      for (const fieldName of Object.keys(data)) {
        const fieldData = data[fieldName];
        if (fieldData.mandatory && !fieldData.value) {
          return {
            fieldName,
            error: 'Mandatory fields are not filled!',
          };
        }
      }
    }

    return {}; // no error
  };

  closeBatchEntry() {
    const { closeBatchEntry, deleteQuickInput, quickInputId } = this.props;

    if (quickInputId) {
      deleteQuickInput();
      closeBatchEntry();
    }
  }

  /**
   * @method resetWidgetValues
   * @summary resets cachedValue for widgets after getting new data
   */
  resetWidgetValues = () => {
    this.rawWidgets.forEach((widget) => widget.resetCachedValue());
  };

  setRef = (refNode) => {
    this.form = refNode;
  };

  setWidgetWrapperElement = (widgetWrapperElement, fieldNames) => {
    const rawWidgetElement = widgetWrapperElement?.getWrappedElement?.();
    if (!rawWidgetElement) {
      return;
    }

    this.rawWidgets.push(rawWidgetElement);

    fieldNames.forEach((fieldName) => {
      this.rawWidgetsByFieldName[fieldName] = rawWidgetElement;
    });
  };

  renderFields = () => {
    const {
      windowId,
      tabId,
      docId,
      forceHeight,
      //
      data,
      layout,
      quickInputId,
      inProgress,
    } = this.props;
    const { isSubmitPending } = this.state;

    if (data && layout) {
      const readonly = isSubmitPending;

      return layout.map((item, idx) => {
        const fieldNames = item.fields.map((elem) => elem.field);
        const widgetData = item.fields.map((elem) => {
          let fieldData = data[elem.field] || {};
          if (readonly && !fieldData.readonly) {
            fieldData = { ...fieldData, readonly: true };
          }
          return fieldData;
        });

        return (
          <WidgetWrapper
            key={idx}
            ref={(node) => this.setWidgetWrapperElement(node, fieldNames)}
            dataSource="quick-input"
            entity={'window'}
            windowType={windowId}
            tabId={tabId}
            subentity="quickInput"
            subentityId={quickInputId}
            inProgress={inProgress}
            widgetType={item.widgetType}
            widgetSize={item.size}
            fields={item.fields}
            dataId={docId}
            widgetData={widgetData}
            gridAlign={item.gridAlign}
            forceFullWidth={widgetData.length > 1}
            forceHeight={forceHeight}
            propagateEnterKeyEvent={true} // make sure Enter key is propagated, so onSubmit is called
            caption={item.caption}
            handlePatch={this.handlePatch}
            handleChange={this.handleChange}
            type="secondary"
            autoFocus={idx === 0}
            initialFocus={idx === 0}
          />
        );
      });
    }
  };

  renderSubmitButton = () => {
    const { isSubmitPending } = this.state;

    return (
      <>
        <div className="col-sm-12 col-md-3 col-lg-2 hint">
          {`(Press 'Enter' to add)`}
        </div>
        {!isSubmitPending && <button type="submit" className="hidden-up" />}
      </>
    );
  };

  /**
   * @method handleClickOutside
   * @summary Whenever we click outside the Quick Input form we set the flag `hasFocus`
   *          This is needed as by default the logic introduced in https://github.com/metasfresh/metasfresh/pull/11163/files
   *          is setting by default the focus when the component receives new props (after the item was introduced its focusing on the first field)
   */
  handleClickOutside = () => this.setState({ hasFocus: false });

  /**
   * @method handleOnClick
   * @summary When click is executed in the form we set the internal `hasFocus` flag indicating that we got the focus
   */
  handleOnClick = () => this.setState({ hasFocus: true });

  render() {
    return (
      <form
        onSubmit={this.onSubmit}
        className="row quick-input-container"
        ref={this.setRef}
        onClick={this.handleOnClick}
      >
        {this.renderFields()}
        {this.renderSubmitButton()}
      </form>
    );
  }
}

const mapStateToProps = ({ tableQuickInputHandler }) => {
  const { quickInputId, layout, data, inProgress } = tableQuickInputHandler;

  return {
    layout,
    data,
    quickInputId,
    inProgress,
  };
};

const mapDispatchToProps = (dispatch, ownProps) => {
  return {
    fetchQuickInputData: (args) => dispatch(fetchQuickInputData({ ...args })),
    // for tests purposes
    fetchQuickInputLayout: (args) =>
      ownProps.fetchQuickInputLayout
        ? ownProps.fetchQuickInputLayout(args, dispatch)
        : dispatch(fetchQuickInputLayout({ ...args })),
    deleteQuickInput: () => dispatch(deleteQuickInput()),
    updateQuickinputData: (args) => dispatch(updateQuickinputData(args)),
    patchQuickInput: (args) => dispatch(patchQuickInput({ ...args })),
  };
};

TableQuickInput.propTypes = {
  windowId: PropTypes.any.isRequired,
  docId: PropTypes.string.isRequired,
  tabId: PropTypes.string.isRequired,
  forceHeight: PropTypes.number,
  closeBatchEntry: PropTypes.func,
  addNotification: PropTypes.func.isRequired,

  //
  // mapStateToProps
  layout: PropTypes.array,
  data: PropTypes.object,
  quickInputId: PropTypes.any,
  inProgress: PropTypes.bool,

  //
  // mapDispatchToProps:
  fetchQuickInputData: PropTypes.func.isRequired,
  fetchQuickInputLayout: PropTypes.func.isRequired,
  deleteQuickInput: PropTypes.func.isRequired,
  updateQuickinputData: PropTypes.func.isRequired,
  patchQuickInput: PropTypes.func.isRequired,
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(onClickOutside(TableQuickInput));

// needed for testing
export { TableQuickInput };
