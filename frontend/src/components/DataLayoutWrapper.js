import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import { fetchViewAttributes, fetchViewAttributesLayout } from '../actions/IndependentWidgetsActions';
import { patchViewAttributes } from '../api';
import { parseToDisplay } from '../utils/documentListHelper';

import SelectionAttributes from './app/SelectionAttributes';

/**
 * @file Class based component.
 * @module DocumentList
 * @extends PureComponent
 */
class DataLayoutWrapper extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      layout: [],
      data: [],
      dataId: null,
    };
  }

  componentDidMount = () => {
    const {
      windowId,
      viewId,
      selected,
      fetchViewAttributes,
      fetchViewAttributesLayout,
    } = this.props;
    const rowId = selected[0];

    console.log('SELECTED: ', rowId, selected)

    this.mounted = true;

    fetchViewAttributesLayout({ windowId, viewId, rowId });
    fetchViewAttributes({ windowId, viewId, rowId });
  };

  componentWillUnmount = () => {
    // const {
    //   windowId,
    //   viewId,
    //   selected,
    //   fetchViewAttributes,
    //   fetchViewAttributesLayout,
    // } = this.props;
    // const rowId = selected[0];

    this.mounted = false;

    // fetchViewAttributesLayout({ windowId, viewId, rowId });
    // fetchViewAttributes({ windowId, viewId, rowId });
  };

  handleChange = (field, value) => {
    this.setState({
      data: {
        ...this.state.data,
        [field]: {
          ...this.state.data[field],
          value,
        },
      },
    });
  };

  handlePatch = (prop, value, cb) => {
    const { windowId, viewId } = this.props;
    const { dataId: rowId } = this.state;

    /*eslint-disable */
    patchViewAttributes(windowId, viewId, rowId, prop, value).then(({ data }) => {
      if (data.length) {
        const preparedData = parseToDisplay(data[0].fieldsByName);
        
        if (preparedData) {
          Object.keys(preparedData).map(key => {
            this.setState({
              data: {
                ...this.state.data,
                [key]: {
                  ...this.state.data[key],
                  ...preparedData[key],
                }
              }
            });
          });
        }
      }

      cb && cb();
    });
    /*eslint-enable */
  };

  setData = (data, dataId, cb) => {
    // console.log('DLW.setData: ', dataId, data)

    const preparedData = parseToDisplay(data);
    this.mounted &&
      this.setState(
        {
          data: preparedData,
          // sometimes it's a number, and React complains about wrong type
          dataId: dataId + '',
        },
        cb
      );
  };

  setLayout = (layout, cb) => {
    // console.log('DLW.setLayout: ', layout);

    this.mounted &&
      this.setState(
        {
          layout,
        },
        cb
      );
  };

  render() {
    const { layout, data, dataId } = this.state;
    const { className } = this.props;

    return (
      <div className={className}>
        <SelectionAttributes
          {...this.props}
          DLWrapperData={data}
          DLWrapperDataId={dataId}
          DLWrapperLayout={layout}
          DLWrapperSetData={this.setData}
          DLWrapperSetLayout={this.setLayout}
          DLWrapperHandleChange={this.handleChange}
          DLWrapperHandlePatch={this.handlePatch}
        />
      </div>
    );
  }
}

DataLayoutWrapper.propTypes = {
  windowId: PropTypes.string,
  viewId: PropTypes.string,
  selected: PropTypes.array,
  inBackground: PropTypes.bool,
  children: PropTypes.node,
  className: PropTypes.string,
  entity: PropTypes.string,
  supportAttribute: PropTypes.bool,
  setClickOutsideLock: PropTypes.func,
  fetchViewAttributes: PropTypes.func,
  fetchViewAttributesLayout: PropTypes.func,
};

// const mapStateToProps = (state, props) => {
//   return {

//   };
// }

export default connect(
  // mapStateToProps,
  null,
  {
    fetchViewAttributes,
    fetchViewAttributesLayout,
  }
)(DataLayoutWrapper);
