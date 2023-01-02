import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import cx from 'classnames';
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
  // local state
  state = { hasFocus: true };

  componentDidMount() {
    this.initQuickInput();
  }

  componentDidUpdate() {
    const { data, layout, inProgress } = this.props;

    // refocus the first widget after update
    if (data && layout && !inProgress) {
      const item = layout[0].fields.map((elem) => data[elem.field] || -1);

      if (!item[0].value) {
        if (this.rawWidgets) {
          this.focusWidgetField();
        }
      }
    }
  }

  /**
   * @method focusWidgetField
   * @summary function to manually focus a widget
   */
  focusWidgetField = () => {
    const { hasFocus } = this.state;
    let curWidget = this.rawWidgets[0];

    if (
      hasFocus &&
      curWidget &&
      curWidget.rawWidget &&
      curWidget.rawWidget.current &&
      curWidget.rawWidget.current.focus
    ) {
      curWidget.rawWidget.current.focus();
    }
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
    const { addNotification, docType, docId, tabId, data, id } = this.props;

    e.preventDefault();

    document.activeElement.blur();

    if (!this.validateForm(data)) {
      return addNotification(
        'Error',
        'Mandatory fields are not filled!',
        5000,
        'error'
      );
    }

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
      .then(this.initQuickInput);
  };

  validateForm = (data) => {
    return !Object.keys(data).filter(
      (key) => data[key].mandatory && !data[key].value
    ).length;
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
    this.rawWidgets.forEach((widget) => {
      widget.resetCachedValue();
    });
  };

  setRef = (refNode) => {
    this.form = refNode;
  };

  setWidgetsRef = (refNode) => {
    refNode &&
      refNode.childRef &&
      refNode.childRef.current &&
      this.rawWidgets.push(refNode.childRef.current);
  };

  renderFields = () => {
    const { tabId, docType, forceHeight, data, layout, id, inProgress, docId } =
      this.props;

    const layoutFieldsAmt = layout ? layout.length : 2;
    const stylingLayout = [
      {
        formGroup: cx(`col-12`, {
          'col-lg-5': layoutFieldsAmt === 2,
          'col-xl-6': layoutFieldsAmt === 2,
          'col-lg-4': layoutFieldsAmt === 3,
          'col-xl-5': layoutFieldsAmt === 3,
        }),
        label: `col-12 col-lg-3 quickInput-label`,
        field: `col-12 col-lg-9`,
      },
      {
        formGroup: `col-12 col-lg-3 col-xl-3`,
        label: `col-12 col-sm-4 col-lg-5 col-xl-4`,
        field: `col-12 col-sm-8 col-lg-7 col-xl-8`,
      },
      {
        formGroup: `col-12 col-lg-3 col-xl-2`,
        label: `col-12 col-sm-9 col-lg-7`,
        field: `col-12 col-sm-3 col-lg-5`,
      },
    ];

    if (data && layout) {
      return layout.map((item, idx) => {
        const widgetData = item.fields.map((elem) => data[elem.field] || -1);
        const lastFormField = idx === layout.length - 1;

        return (
          <WidgetWrapper
            ref={this.setWidgetsRef}
            dataSource="quick-input"
            fieldFormGroupClass={stylingLayout[idx].formGroup}
            fieldLabelClass={stylingLayout[idx].label}
            fieldInputClass={stylingLayout[idx].field}
            inProgress={inProgress}
            entity={'window'}
            subentity="quickInput"
            subentityId={id}
            tabId={tabId}
            windowType={docType}
            widgetType={item.widgetType}
            fields={item.fields}
            dataId={docId}
            widgetData={widgetData}
            gridAlign={item.gridAlign}
            forceFullWidth={widgetData.length > 1}
            forceHeight={forceHeight}
            key={idx}
            lastFormField={lastFormField}
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
