import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';

import { referencesEventSource } from '../../actions/GenericActions';
import { setFilter } from '../../actions/ListActions';
import Loader from '../app/Loader';

/**
 * Document related documents (references) component
 */
class Referenced extends Component {
  constructor(props) {
    super(props);

    this.state = {
      loading: true,
      data: null,
    };
  }

  componentDidMount = () => {
    const { windowType, docId } = this.props;

    referencesEventSource({
      windowId: windowType,
      documentId: docId,

      onPartialResult: (partialGroup) => {
        const data = this.state.data || [];
        this.setState(
          {
            ...this.state,
            loading: true,
            data: this.mergePartialGroupToGroupsArray(data, partialGroup),
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

  mergePartialGroupToGroupsArray(groups, partialGroup) {
    if (!partialGroup) {
      return groups;
    }

    let result = [];
    let partialGroupMerged = false;

    for (const group of groups) {
      if (!partialGroupMerged && group.caption === partialGroup.caption) {
        const changedGroup = this.mergeReferencesToGroup(
          group,
          partialGroup.references
        );

        result.push(changedGroup);
        partialGroupMerged = true;
      } else {
        result.push(group);
      }
    }

    if (!partialGroupMerged) {
      result.push(partialGroup);
      partialGroupMerged = true;
    }

    //
    // Sort groups by caption alphabetically, keep miscGroup last.
    result = result.sort((group1, group2) => {
      if (group1.miscGroup == group2.miscGroup) {
        return group1.caption.localeCompare(group2.caption);
      } else if (group1.miscGroup) {
        return +1; // keep misc group last
      } else {
        return -1;
      }
    });

    return result;
  }

  mergeReferencesToGroup(group, referencesToAdd) {
    if (!referencesToAdd || !referencesToAdd.length) {
      return group;
    }

    const referencesToAddById = {};
    referencesToAdd.forEach((reference) => {
      referencesToAddById[reference.id] = reference;
    });

    let references = [];

    for (const existingReference of group.references) {
      const referenceToAdd = referencesToAddById[existingReference.id];
      delete referencesToAddById[existingReference.id];

      if (
        referenceToAdd &&
        referenceToAdd.priority < existingReference.priority
      ) {
        references.push(referenceToAdd);
      } else {
        references.push(existingReference);
      }
    }

    Object.values(referencesToAddById).forEach((referenceToAdd) =>
      references.push(referenceToAdd)
    );

    references = references.sort((reference1, reference2) => {
      return reference1.caption.localeCompare(reference2.caption);
    });

    return { ...group, references };
  }

  handleReferenceClick = (type, filter) => {
    const { dispatch, windowType, docId } = this.props;

    dispatch(setFilter(filter, type));
    dispatch(push(`/window/${type}?refType=${windowType}&refId=${docId}`));
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

  renderData = () => {
    const { loading, data } = this.state;

    if (data && data.length) {
      return data.map((item) => {
        return [
          <div
            key="caption"
            className={`subheader-caption reference_${item.internalName}`}
          >
            {item.caption}
          </div>,
        ].concat(
          item.references.map((ref, refKey) => (
            <div
              className={`subheader-item js-subheader-item reference_${
                ref.internalName
              }`}
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
      });
    } else if (!loading) {
      return (
        <div className="subheader-item subheader-item-disabled">
          {counterpart.translate('window.sideList.referenced.empty')}
        </div>
      );
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
        {loading ? <Loader /> : null}
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
