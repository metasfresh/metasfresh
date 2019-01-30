import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import classnames from 'classnames';
import { Set } from 'immutable';

import { activateTab, unselectTab } from '../../actions/WindowActions';
import Tab from './Tab';

class Tabs extends Component {
  constructor(props) {
    super(props);

    const firstTab = props.tabs.find(
      tab => tab.tabId === props.children[0].key
    );
    const selected = this.getSelected(firstTab, Set());

    this.state = {
      selected,
    };
  }

  getSelected = (tab, selected) => {
    selected = selected.add(tab.tabId);

    if (tab.tabs) {
      this.getSelected(tab.tabs[0], selected);
    }

    return selected;
  };

  componentDidMount = () => {
    this.props.dispatch(activateTab('master', this.state.selected));
  };

  componentDidUpdate = (prevProps, prevState) => {
    const { dispatch } = this.props;
    if (prevState.selected !== this.state.selected) {
      dispatch(activateTab('master', this.state.selected));
    }
  };

  componentWillUnmount() {
    this.props.dispatch(unselectTab('master'));
  }

  handleClick = (e, id) => {
    e.preventDefault();
    this.setState({
      selected: id,
    });
  };

  handlePillKeyDown = (e, key) => {
    if (e.key === 'Enter') {
      this.handleClick(e, key);
    }
  };

  renderNestedPills = (parentItem, maxWidth, level, nestedPills) => {
    const pillsArray = parentItem.tabs.map(item => {
      if (item.tabs) {
        this.renderNestedPills(item, maxWidth, level++, nestedPills);
      }

      return this.renderPill(item, maxWidth);
    });

    nestedPills[level] = [
      <ul
        key={`nested-tabs-${parentItem.tabId}`}
        className="nav nav-tabs nested-tabs"
      >
        {pillsArray}
      </ul>,
    ];
  };

  renderPill = (item, maxWidth) => {
    const { tabIndex, modalVisible } = this.props;
    const { selected } = this.state;

    return (
      <li
        id={`tab_${item.internalName}`}
        className="nav-item"
        key={'tab-' + item.tabId}
        onClick={e => this.handleClick(e, item.tabId)}
        tabIndex={modalVisible ? -1 : tabIndex}
        onKeyDown={e => this.handlePillKeyDown(e, item.tabId)}
        style={{ maxWidth }}
        title={item.description || item.caption}
      >
        <a
          className={classnames('nav-link', {
            active: selected.has(item.tabId),
          })}
        >
          {item.caption}
        </a>
      </li>
    );
  };

  renderPills = pills => {
    const maxWidth = 95 / pills.length + '%';
    const { selected } = this.state;
    const nestedPills = [];

    const pillsArray = pills.map(item => {
      if (item.tabs && selected.has(item.tabId)) {
        this.renderNestedPills(item, maxWidth, 0, nestedPills);
      }

      return this.renderPill(item, maxWidth);
    });

    return (
      <div className="tabs-wrap">
        <ul className="nav nav-tabs mt-1">{pillsArray}</ul>
        {nestedPills}
      </div>
    );
  };

  renderTabs = tabs => {
    const { toggleTableFullScreen, fullScreen, windowType } = this.props;
    const { selected } = this.state;

    return tabs.map(item => {
      const itemWithProps = Object.assign({}, item, {
        props: Object.assign({}, item.props, {
          toggleFullScreen: toggleTableFullScreen,
          fullScreen: fullScreen,
        }),
      });

      if (selected.has(item.key)) {
        const { tabId, queryOnActivate, docId, orderBy } = item.props;

        return (
          <div key={'pane' + item.key} className="tab-pane active">
            <Tab
              {...{
                queryOnActivate,
                tabId,
                docId,
                windowType,
                orderBy,
              }}
            >
              {itemWithProps}
            </Tab>
          </div>
        );
      } else {
        return false;
      }
    });
  };

  render() {
    const { children, fullScreen, tabs } = this.props;

    return (
      <div
        className={classnames('mb-1', {
          'tabs-fullscreen container-fluid': fullScreen,
        })}
      >
        {this.renderPills(tabs)}
        <div className="tab-content" ref={c => (this.tabContent = c)}>
          {this.renderTabs(children)}
        </div>
      </div>
    );
  }
}

Tabs.propTypes = {
  tabs: PropTypes.array,
  dispatch: PropTypes.func.isRequired,
  modalVisible: PropTypes.bool.isRequired,
};

export default connect(state => ({
  modalVisible: state.windowHandler.modal.visible,
}))(Tabs);
