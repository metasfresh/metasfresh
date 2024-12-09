import React, { Component } from 'react';
import PropTypes from 'prop-types';
<<<<<<< HEAD
=======
import Spinner from '../app/SpinnerOverlay';
import counterpart from 'counterpart';
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

class MultiSelect extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: this.props.options,
      checkedItems: {},
    };
  }

  componentDidMount() {
    this.props.onFocus();
  }

  static getDerivedStateFromProps(nextProps) {
    let valuesPart =
      nextProps.selectedItems !== null ? nextProps.selectedItems.values : null;
    let selected =
      nextProps.selectedItems && Array.isArray(nextProps.selectedItems)
        ? nextProps.selectedItems
        : valuesPart;
    if (selected !== null && typeof selected !== 'undefined') {
      let updatedCheckedItems = {};
      selected.map((item) => {
        if (item) {
          updatedCheckedItems[item.key] = {
            key: item.key,
            caption: item.caption,
            value: true,
          };
        }
        return item;
      });
      return {
        checkedItems: updatedCheckedItems,
      };
    }

    return null;
  }

  selectItem = (key, caption) => {
    let selected = null;
    let newCheckedItems = JSON.parse(JSON.stringify(this.state.checkedItems));

    if (typeof this.state.checkedItems[key] === 'undefined') {
      newCheckedItems[key] = { key, caption, value: true };
    } else {
      newCheckedItems[key].value = !this.state.checkedItems[key].value;
    }
    this.setState({ checkedItems: newCheckedItems });
    selected = Object.keys(newCheckedItems).map((item) => {
      if (newCheckedItems[item].value === true) {
        return {
          key: newCheckedItems[item].key,
          caption: newCheckedItems[item].caption,
        };
      }
    });

    this.props.onSelect(selected.filter((el) => typeof el !== 'undefined'));
  };

  render() {
<<<<<<< HEAD
=======
    const { loading, hasMoreResults } = this.props;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    const { data, checkedItems } = this.state;
    const dataSource = data.length > 0 ? data : this.props.options;

    return (
      <div className="filter-multiselect">
<<<<<<< HEAD
=======
        {loading && <Spinner iconSize={25} />}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
        <div>
          {dataSource.map((item) => (
            <div className="form-group" key={item.key}>
              <div key={item.key} className="row">
<<<<<<< HEAD
                <div className=" col-sm-6 float-left">
                  <label className="form-control-label" title={item.caption}>
                    {item.caption}
                  </label>
                </div>

                <div className="col-sm-6 float-right">
                  <label className="input-checkbox">
                    <input
=======
                <div className=" col-sm-11 float-left col-label">
                  <label
                    className="form-control-label"
                    title={item.caption}
                    htmlFor={'chk_' + item.key}
                  >
                    {item.caption}
                  </label>
                </div>
                <div className="col-sm-1 float-right col-chk">
                  <label className="input-checkbox">
                    <input
                      id={'chk_' + item.key}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
                      type="checkbox"
                      onChange={() => this.selectItem(item.key, item.caption)}
                      checked={
                        checkedItems[item.key]
                          ? checkedItems[item.key].value
                          : false
                      }
                    />
                    <span className="input-checkbox-tick" />
                  </label>
                </div>
              </div>
            </div>
          ))}
<<<<<<< HEAD
=======
          {hasMoreResults && (
            <div>({counterpart.translate('widget.lookup.hasMoreResults')})</div>
          )}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
        </div>
      </div>
    );
  }
}

MultiSelect.propTypes = {
  options: PropTypes.array,
<<<<<<< HEAD
=======
  loading: PropTypes.bool,
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
  onFocus: PropTypes.func,
  onSelect: PropTypes.func,
  selectedItems: PropTypes.any,
};

export default MultiSelect;
