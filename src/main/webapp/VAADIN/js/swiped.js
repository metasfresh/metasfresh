(function() {
    var msPointer = window.navigator.msPointerEnabled;

    var touch = {
        start: msPointer ? 'MSPointerDown' : 'touchstart',
        move: msPointer ? 'MSPointerMove' : 'touchmove',
        end: msPointer ? 'MSPointerUp' : 'touchend'
    };

    var prefix = (function () {
        var styles = window.getComputedStyle(document.documentElement, '');
        var pre = (Array.prototype.slice
                .call(styles)
                .join('')
                .match(/-(moz|webkit|ms)-/) || (styles.OLink === '' && ['', 'o'])
            )[1];

        return '-' + pre + '-';
    })();

    var transitionEvent = (function() {
        var t,
            el = document.createElement("fakeelement");

        var transitions = {
            "transition"      : "transitionend",
            "OTransition"     : "oTransitionEnd",
            "MozTransition"   : "transitionend",
            "WebkitTransition": "webkitTransitionEnd"
        };

        for (t in transitions){
            if (el.style[t] !== undefined){
                return transitions[t];
            }
        }
    })();

    var cssProps = {
        'transition': prefix + 'transition',
        'transform': prefix + 'transform'
    };

    function delegate(event, cbName) {
        document.addEventListener(event, function(e) {
            Swiped._elems.forEach(function(Swiped){
                var target = e.target;

                while (target) {
                    if (target === Swiped.elem) {
                        Swiped[cbName](e);

                        return false;
                    }
                    target = target.parentNode;
                }

                return false;
            });

        });
    }

    function extend() {
        var current = [].shift.call(arguments);
        var options = arguments[0];

        for (var i in options) {
            if (options.hasOwnProperty(i)) {
                current[i] = options[i];
            }
        }

        return current;
    }

    var fn = function() {};

    var Swiped = function(o) {
        var defaultOptions = {
            duration: 200,
            tolerance: 50,
            time: 200,
            dir: 1,
            right: 0,
            left: 0
        };

        o = extend(defaultOptions, o || {});

        this.duration = o.duration;
        this.tolerance = o.tolerance;
        this.time = o.time;
        this.width = o.left || o.right;
        this.elem = o.elem;
        this.list = o.list;
        this.dir = o.dir;
        this.group = o.group;
        this.id = Swiped.elemId++;

        this.onOpen = typeof o.onOpen === 'function' ? o.onOpen : fn;
        this.onClose = typeof o.onClose === 'function' ? o.onClose : fn;

        this.right = o.right;
        this.left = o.left;

        if (
            (o.right > 0 && o.tolerance > o.right) ||
            (o.left > 0 && o.tolerance > o.left)
        ) {
            console.warn('tolerance must be less then left and right');
        }
    };

    Swiped._elems = [];
    Swiped.groupCounter = 0;
    Swiped.elemId = 0;

    Swiped.init = function(o) {
        Swiped.groupCounter++;

        var elems = document.querySelectorAll(o.query);
        var group = [];

        delete o.query;

        [].forEach.call(elems, function(elem){
            var option = extend({elem: elem, group: Swiped.groupCounter}, o);

            group.push(new Swiped(option));
        });

        Swiped._bindEvents();
        Swiped._elems = Swiped._elems.concat(group);

        if (group.length === 1) {
            return group[0];
        }

        return group;
    };

    Swiped._closeAll = function(groupNumber) {
        Swiped._elems.forEach(function(Swiped) {
            if (Swiped.group === groupNumber) {
                Swiped.close(true);
            }
        });
    };

    Swiped.prototype.transitionEnd = function(node, cb) {
        var that = this;

        function trEnd() {
            cb.call(that);
            node.removeEventListener(transitionEvent, trEnd);
        }

        node.addEventListener(transitionEvent, trEnd);
    };

    /**
     * swipe.x - initial coordinate Х
     * swipe.y - initial coordinate Y
     * swipe.delta - distance
     * swipe.startSwipe - swipe is starting
     * swipe.startScroll - scroll is starting
     * swipe.startTime - necessary for the short swipe
     * swipe.touchId - ID of the first touch
     */

    Swiped.prototype.touchStart = function(e) {
        var touch = e.changedTouches[0];

        if (e.touches.length !== 1) {
            return;
        }

        this.touchId = touch.identifier;
        this.x = touch.pageX;
        this.y = touch.pageY;
        this.startTime = new Date();

        this.resetValue();

        if (this.list) {
            Swiped._closeAll(this.group);
        } else {
            this.close(true);
        }
    };

    Swiped.prototype.touchMove = function(e) {
        var touch = e.changedTouches[0];

        // touch of the other finger
        if (!this.isValidTouch(e)) {
            return;
        }

        this.delta = touch.pageX - this.x;

        this.dir = this.delta < 0 ? -1 : 1;
        this.width = this.delta < 0 ? this.right : this.left;

        this.defineUserAction(touch);

        if (this.startSwipe) {
            this.move();

            //prevent scroll
            e.preventDefault();
        }
    };

    Swiped.prototype.touchEnd = function(e) {
        if (!this.isValidTouch(e, true) || !this.startSwipe) {
            return;
        }

        // if swipe is more then 150px or time is less then 150ms
        if (this.dir * this.delta > this.tolerance || new Date() - this.startTime < this.time) {
            this.open();
        } else {
            this.close();
        }

        e.stopPropagation();
        e.preventDefault();
    };

    /**
     * Animation of the opening
     */
    Swiped.prototype.open = function(isForce) {
        this.animation(this.dir * this.width);
        this.swiped = true;

        if (!isForce) {
            this.transitionEnd(this.elem, this.onOpen);
        }

        this.resetValue();
    };

    /**
     * Animation of the closing
     */
    Swiped.prototype.close = function(isForce) {
        this.animation(0);
        this.swiped = false;

        if (!isForce) {
            this.transitionEnd(this.elem, this.onClose);
        }

        this.resetValue();
    };

    Swiped.prototype.toggle = function() {
        if (this.swiped) {
            this.close();
        } else {
            this.open();
        }
    };

    /**
     * reset to initial values
     */
    Swiped.prototype.resetValue = function() {
        this.startSwipe = false;
        this.startScroll = false;
        this.delta = 0;
    };

    Swiped._bindEvents = function() {
        if (Swiped.eventBinded) {
            return false;
        }

        delegate(touch.move, 'touchMove');
        delegate(touch.end, 'touchEnd');
        delegate(touch.start, 'touchStart');

        Swiped.eventBinded = true;
    };

    /**
     * detect of the user action: swipe or scroll
     */
    Swiped.prototype.defineUserAction = function(touch) {
        var DELTA_X = 10;
        var DELTA_Y = 10;

        if (Math.abs(this.y - touch.pageY) > DELTA_Y && !this.startSwipe) {
            this.startScroll = true;
        } else if (Math.abs(this.delta) > DELTA_X && !this.startScroll) {
            this.startSwipe = true;
        }
    };

    /**
     * Which of the touch was a first, if it's a multitouch
     * touchId saved on touchstart
     * @param {object} e - event
     * @returns {boolean}
     */
    Swiped.prototype.isValidTouch = function(e, isTouchEnd) {
        // take a targetTouches because need events on this node
        // targetTouches is empty in touchEnd, therefore take a changedTouches
        var touches = isTouchEnd ? 'changedTouches' : 'targetTouches';

        return e[touches][0].identifier === this.touchId;
    };

    Swiped.prototype.move = function() {
        if ((this.dir > 0 && (this.delta < 0 || this.left === 0)) || (this.dir < 0 && (this.delta > 0 || this.right === 0))) {
            return false;
        }

        var deltaAbs = Math.abs(this.delta);

        if (deltaAbs > this.width) {
            // linear deceleration
            this.delta = this.dir * (this.width + (deltaAbs - this.width) / 8);
        }

        this.animation(this.delta, 0);
    };

    Swiped.prototype.animation = function(x, duration) {
        duration = duration === undefined ? this.duration : duration;

        this.elem.style.cssText = cssProps.transition + ':' + cssProps.transform + ' ' + duration + 'ms; ' +
        cssProps.transform  + ':' + 'translate3d(' + x + 'px, 0px, 0px)';
    };

    Swiped.prototype.destroy = function(isRemoveNode) {
        var id = this.id;

        Swiped._elems.forEach(function(elem, i) {
            if (elem.id === id) {
                Swiped._elems.splice(i, 1);
            }
        });

        if (isRemoveNode) {
            this.elem.parentNode.removeChild(this.elem);
        }
    };

    // expose Swiped
    window.Swiped = Swiped;
})();
