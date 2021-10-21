import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import classnames from 'classnames';

class ViewHeader extends Component {
  render() {
    const { headersEntries } = this.props;
    //console.log('************* renderHeaderLanes: ', headersEntries);

    return (
      <div className={classnames({ 'header-caption': headersEntries })}>{this.renderHeaderLanes(headersEntries)}</div>
    );
  }

  renderHeaderLanes(headersEntries) {
    if (!headersEntries) {
      return null;
    }

    let nextIndex = 0;
    return headersEntries.map((headersEntry) => {
      const laneComponent = this.renderHeaderLane(headersEntry, nextIndex);

      if (laneComponent) {
        nextIndex++;
      }

      return laneComponent;
    });
  }

  renderHeaderLane(headersEntry, idx) {
    if (headersEntry.hidden) {
      return null;
    }

    return (
      <div
        key={idx}
        className={classnames(
          'picking-step-details centered-text is-size-6',
          `py-4`,
          `px-${idx + 4}`,
          `header_info_${idx}`
        )}
      >
        {headersEntry.values &&
          headersEntry.values.map(({ caption, value }, i) => {
            return (
              <div key={i} className="columns is-mobile is-size-7">
                <div className="column is-half has-text-left has-text-weight-bold pt-0 pb-0 pl-1 pr-0">{caption}:</div>
                <div className={classnames('column is-half has-text-left pt-0 pb-0', { 'is-size-4': idx === 1 })}>
                  {value}
                </div>
              </div>
            );
          })}
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    headersEntries: state.headers.entries,
  };
};

ViewHeader.propTypes = {
  //
  // Props:
  headersEntries: PropTypes.array.isRequired,
};

export default connect(mapStateToProps, null)(ViewHeader);
