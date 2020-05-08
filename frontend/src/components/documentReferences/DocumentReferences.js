import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';

import { referencesEventSource } from '../../actions/GenericActions';
import { setFilter } from '../../actions/ListActions';
import { mergePartialGroupToGroupsArray } from '../../utils/documentReferencesHelper';
import Loader from '../app/Loader';

import DocumentReferenceGroup from './DocumentReferenceGroup';

/**
 * Document related documents (references) component
 */
class DocumentReferences extends Component {
  constructor(props) {
    super(props);

    this.state = {
      loading: true,
      data: null,
    };
  }

  componentDidMount = () => {
    const { windowId, documentId } = this.props;

    referencesEventSource({
      windowId: windowId,
      documentId: documentId,

      onPartialResult: (partialGroup) => {
        const data = this.state.data || [];
        this.setState(
          {
            ...this.state,
            loading: true,
            data: mergePartialGroupToGroupsArray(data, partialGroup),
          },
          () => {
            this.referenced && this.referenced.focus();
          }
        );
      },

      onComplete: () => {
        this.setState({
          ...this.state,
          loading: false,
        });
      },
    });
  };

  handleReferenceClick = (targetWindowId, filter) => {
    const { dispatch, windowId, documentId } = this.props;

    dispatch(setFilter(filter, targetWindowId));
    dispatch(
      push(`/window/${targetWindowId}?refType=${windowId}&refId=${documentId}`)
    );
  };

  handleKeyDown = (e) => {
    const active = document.activeElement;

    const keyHandler = (e, dir) => {
      e.preventDefault();

      const sib = dir ? 'nextSibling' : 'previousSibling';

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

  render() {
    const { loading } = this.state;
    return (
      <div
        onKeyDown={this.handleKeyDown}
        ref={(c) => (this.referenced = c)}
        tabIndex={0}
      >
        {this.renderData()}
        {loading ? <Loader key="loader" /> : null}
      </div>
    );
  }

  renderData = () => {
    const { loading, data } = this.state;

    if (data && data.length) {
      return data.map((group) => (
        <DocumentReferenceGroup
          key={group.caption}
          caption={group.caption}
          references={group.references}
          onReferenceItemClick={this.handleReferenceClick}
        />
      ));
    } else if (!loading) {
      return (
        <div className="subheader-item subheader-item-disabled">
          {counterpart.translate('window.sideList.referenced.empty')}
        </div>
      );
    }
  };
}

DocumentReferences.propTypes = {
  windowId: PropTypes.string.isRequired,
  documentId: PropTypes.string.isRequired,
  dispatch: PropTypes.func.isRequired,
};

export default connect()(DocumentReferences);
