import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';

import { addNotification } from '../../actions/AppActions';
import {
  completeRequest,
  createInstance,
  patchRequest,
} from '../../actions/GenericActions';
import { initLayout } from '../../api';
import { addNewRow, parseToDisplay } from '../../actions/WindowActions';
import RawWidget from '../widget/RawWidget';

class TableQuickInput extends Component {
  // promise with patching for queuing form submission after patch is done
  patchPromise;

  constructor(props) {
    super(props);

    this.state = {
      layout: null,
      data: null,
      id: null,
      editedField: 0,
    };
  }

  componentDidMount() {
    this.initQuickInput();
  }

  componentDidUpdate() {
    const { data, layout, editedField } = this.state;

    if (data && layout) {
      for (let i = 0; i < layout.length; i++) {
        const item = layout[i].fields.map(elem => data[elem.field] || -1);

        if (!item[0].value) {
          if (editedField !== i) {
            this.setState(
              {
                editedField: i,
              },
              () => {
                if (this.rawWidgets) {
                  let curWidget = this.rawWidgets[i];
                  if (curWidget && curWidget.focus) {
                    curWidget.focus();
                  }
                }
              }
            );
          }

          break;
        }
      }
    }
  }

  initQuickInput = () => {
    const { dispatch, docType, docId, tabId, closeBatchEntry } = this.props;
    const { layout } = this.state;

    this.setState(
      {
        data: null,
      },
      () => {
        createInstance('window', docType, docId, tabId, 'quickInput')
          .then(instance => {
            this.setState({
              data: parseToDisplay(instance.data.fieldsByName),
              id: instance.data.id,
              editedField: 0,
            });
          })
          .catch(err => {
            if (err.response.status === 404) {
              dispatch(
                addNotification(
                  'Batch entry error',
                  'Batch entry is not available.',
                  5000,
                  'error'
                )
              );
              closeBatchEntry();
            }
          });

        !layout &&
          initLayout('window', docType, tabId, 'quickInput', docId).then(
            layout => {
              this.setState({
                layout: layout.data.elements,
              });
            }
          );
      }
    );
  };

  handleChange = (field, value) => {
    this.setState(prevState => ({
      data: Object.assign({}, prevState.data, {
        [field]: Object.assign({}, prevState.data[field], {
          value,
        }),
      }),
    }));
  };

  handlePatch = (prop, value, callback) => {
    const { docType, docId, tabId } = this.props;
    const { id } = this.state;

    this.patchPromise = new Promise(resolve => {
      patchRequest({
        entity: 'window',
        docType,
        docId,
        tabId,
        property: prop,
        value,
        subentity: 'quickInput',
        subentityId: id,
      }).then(response => {
        const fields = response.data[0] && response.data[0].fieldsByName;

        fields &&
          Object.keys(fields).map(fieldName => {
            this.setState(
              prevState => ({
                data: Object.assign({}, prevState.data, {
                  [fieldName]: Object.assign(
                    {},
                    prevState.data[fieldName],
                    fields[fieldName]
                  ),
                }),
              }),
              () => {
                if (callback) {
                  callback();
                }
                resolve();
              }
            );
          });
      });
    });
  };

  handleBlurWidget = () => {};

  renderFields = (layout, data, dataId, attributeType, quickInputId) => {
    const { tabId, docType } = this.props;

    this.rawWidgets = [];

    if (data && layout) {
      return layout.map((item, id) => {
        const widgetData = item.fields.map(elem => data[elem.field] || -1);

        return (
          <RawWidget
            ref={c => {
              if (c) {
                this.rawWidgets.push(c);
              }
            }}
            entity={attributeType}
            subentity="quickInput"
            subentityId={quickInputId}
            tabId={tabId}
            windowType={docType}
            widgetType={item.widgetType}
            fields={item.fields}
            dataId={dataId}
            widgetData={widgetData}
            gridAlign={item.gridAlign}
            forceFullWidth={widgetData.length > 1}
            key={id}
            caption={item.caption}
            handlePatch={(prop, value, callback) =>
              this.handlePatch(prop, value, callback)
            }
            handleFocus={() => {}}
            handleChange={this.handleChange}
            onBlurWidget={this.handleBlurWidget}
            type="secondary"
            autoFocus={id === 0}
            initialFocus={id === 0}
          />
        );
      });
    }
  };

  onSubmit = e => {
    const { dispatch, docType, docId, tabId } = this.props;
    const { id, data } = this.state;
    e.preventDefault();

    document.activeElement.blur();

    if (!this.validateForm(data)) {
      return dispatch(
        addNotification(
          'Error',
          'Mandatory fields are not filled!',
          5000,
          'error'
        )
      );
    }

    this.patchPromise
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
      .then(response => {
        this.initQuickInput();
        dispatch(
          addNewRow(response.data, tabId, response.data.rowId, 'master')
        );
      });
  };

  validateForm = data => {
    return !Object.keys(data).filter(
      key => data[key].mandatory && !data[key].value
    ).length;
  };

  render() {
    const { docId } = this.props;

    const { data, layout, id } = this.state;

    return (
      <form
        onSubmit={this.onSubmit}
        className="quick-input-container"
        ref={c => (this.form = c)}
      >
        {this.renderFields(layout, data, docId, 'window', id)}
        <div className="hint">{"(Press 'Enter' to add)"}</div>
        <button type="submit" className="hidden-up" />
      </form>
    );
  }
}

TableQuickInput.propTypes = {
  dispatch: PropTypes.func.isRequired,
};

export default connect()(TableQuickInput);
