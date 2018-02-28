import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';

import { referencesRequest } from '../../actions/GenericActions';
import { setFilter } from '../../actions/ListActions';
import Loader from '../app/Loader';

class Referenced extends Component {
  constructor(props) {
    super(props);

    this.state = {
      data: null,
    };
  }

  componentDidMount = () => {
    const { windowType, docId } = this.props;

    referencesRequest('window', windowType, docId).then(response => {
      this.setState(
        {
          data: response.data.groups,
        },
        () => {
          this.referenced && this.referenced.focus();
        }
      );
    });
  };

  handleReferenceClick = (type, filter) => {
    const { dispatch, windowType, docId } = this.props;
    dispatch(setFilter(filter, type));
    dispatch(
      push('/window/' + type + '?refType=' + windowType + '&refId=' + docId)
    );
  };

  handleKeyDown = e => {
    const active = document.activeElement;

    const keyHandler = (e, dir) => {
      const sib = dir ? 'nextSibling' : 'previousSibling';
      e.preventDefault();
      if (active.classList.contains('js-subheader-item')) {
        if (!active[sib]) {
          return;
        }
        if (active[sib].classList.contains('js-subheader-item')) {
          active[sib].focus();
        } else {
          active[sib][sib] && active[sib][sib].focus();
        }
      } else {
        active.getElementsByClassName('js-subheader-item')[0].focus();
      }
    };

    switch (e.key) {
      case 'ArrowDown':
        keyHandler(e, true);
        break;
      case 'ArrowUp':
        keyHandler(e, false);
        break;
      case 'Enter':
        e.preventDefault();
        document.activeElement.click();
        break;
    }
  };

  renderData = () => {
    const { data } = this.state;

    return data && data.length ? (
      data.map(item => {
        return [
          <div key="caption" className="subheader-caption">
            {item.caption}
          </div>,
        ].concat(
          item.references.map((ref, refKey) => (
            <div
              className="subheader-item js-subheader-item"
              onClick={() => {
                this.handleReferenceClick(ref.documentType, ref.filter);
              }}
              key={refKey}
              tabIndex={0}
            >
              {ref.caption}
            </div>
          ))
        );
      })
    ) : (
      <div className="subheader-item subheader-item-disabled">
        {counterpart.translate('window.sideList.referenced.empty')}
      </div>
    );
  };

  render() {
    const { data } = this.state;
    return (
      <div
        onKeyDown={this.handleKeyDown}
        ref={c => (this.referenced = c)}
        tabIndex={0}
      >
        {!data ? <Loader /> : this.renderData()}
      </div>
    );
  }
}

Referenced.propTypes = {
  windowType: PropTypes.string.isRequired,
  docId: PropTypes.string.isRequired,
  dispatch: PropTypes.func.isRequired,
};

export default connect()(Referenced);
