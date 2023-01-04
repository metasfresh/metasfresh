import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import onClickOutside from 'react-onclickoutside';
import { completeRequest } from '../../api';
import {
  fetchQuickInputData,
  fetchQuickInputLayout,
  deleteQuickInput,
  setQuickinputData,
  patchQuickInput,
} from '../../actions/IndependentWidgetsActions';

import WidgetWrapper from '../../containers/WidgetWrapper';

class TableQuickInput extends PureComponent {
  // promise with patching for queuing form submission after patch is done
  patchPromise;
  // widgets refs
  rawWidgets = [];
  rawWidgetsByFieldName = {};
  // local state
  state = { hasFocus: true };

  componentDidMount() {
    this.initQuickInput();
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
      addNotification,
      docType,
      docId,
      tabId,
      fetchQuickInputData,
      fetchQuickInputLayout,
    } = this.props;

    await fetchQuickInputData({
      windowId: docType,
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
      windowId: docType,
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
    const { setQuickinputData } = this.props;
    const fieldData = {};
    fieldData[field] = { value };

    setQuickinputData(fieldData);
  };

  handlePatch = (prop, value, callback) => {
    const { docType, docId, tabId, patchQuickInput } = this.props;

    this.patchPromise = new Promise((resolve) => {
      patchQuickInput({ windowId: docType, docId, tabId, prop, value }).then(
        () => {
          if (callback) {
            callback();
          }
          resolve();
        }
      );
    });
  };

  onSubmit = (e) => {
    if (this.state.submitPending) {
      return;
    }

    const { addNotification, docType, docId, tabId, id } = this.props;

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

    this.setState({ submitPending: true });

    return this.patchPromise
      .then(() => {
        return completeRequest(
          'window',
          docType,
          docId,
          tabId,
          null,
          'quickInput',
          id
        );
      })
      .then(() => this.setState({ submitPending: false }))
      .then(this.initQuickInput);
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
    const { closeBatchEntry, deleteQuickInput, id } = this.props;

    if (id) {
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
    const { tabId, docType, forceHeight, data, layout, id, inProgress, docId } =
      this.props;
    const { submitPending } = this.state;

    if (data && layout) {
      return layout.map((item, idx) => {
        const widgetData = item.fields.map((elem) => data[elem.field] || -1);
        const fieldNames = item.fields.map((elem) => elem.field);

        return (
          <WidgetWrapper
            ref={(node) => this.setWidgetWrapperElement(node, fieldNames)}
            dataSource="quick-input"
            inProgress={inProgress}
            isEditable={!submitPending}
            entity={'window'}
            subentity="quickInput"
            subentityId={id}
            tabId={tabId}
            windowType={docType}
            widgetType={item.widgetType}
            widgetSize={item.size}
            fields={item.fields}
            dataId={docId}
            widgetData={widgetData}
            gridAlign={item.gridAlign}
            forceFullWidth={widgetData.length > 1}
            forceHeight={forceHeight}
            key={idx}
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

  /**
   * @method handleClickOutside
   * @summary Whenever we click outside of the Quick Input form we set the flag `hasFocus`
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
        <div className="col-sm-12 col-md-3 col-lg-2 hint">
          {`(Press 'Enter' to add)`}
        </div>
        <button type="submit" className="hidden-up" />
      </form>
    );
  }
}

const mapStateToProps = ({ widgetHandler }) => {
  const { layout, data, id, inProgress } = widgetHandler.quickInput;

  return {
    layout,
    data,
    id,
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
    setQuickinputData: (args) => dispatch(setQuickinputData(args)),
    patchQuickInput: (args) => dispatch(patchQuickInput({ ...args })),
  };
};

TableQuickInput.propTypes = {
  addNotification: PropTypes.func.isRequired,
  closeBatchEntry: PropTypes.func,
  forceHeight: PropTypes.number,
  docType: PropTypes.any,
  docId: PropTypes.string,
  tabId: PropTypes.string,
  layout: PropTypes.array,
  data: PropTypes.object,
  id: PropTypes.any,
  inProgress: PropTypes.bool,
  fetchQuickInputData: PropTypes.func.isRequired,
  fetchQuickInputLayout: PropTypes.func.isRequired,
  deleteQuickInput: PropTypes.func.isRequired,
  setQuickinputData: PropTypes.func.isRequired,
  patchQuickInput: PropTypes.func.isRequired,
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(onClickOutside(TableQuickInput));

export { TableQuickInput };
