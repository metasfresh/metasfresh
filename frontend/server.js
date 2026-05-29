var webpack = require('webpack');
var WebpackDevServer = require('webpack-dev-server');
var config = require('./webpack.config');
const path = require('path');

var listenHost = process.env.DOCKER ? '0.0.0.0' : 'localhost';

// Add progress plugin to existing config
const configWithProgress = {
  ...config,
  plugins: [
    ...config.plugins,
    new webpack.ProgressPlugin({
      activeModules: true,
      entries: true,
      modules: true,
      dependencies: true,
      handler(percentage, message, ...args) {
        // Only show progress every 5% to avoid spam
        if (
          percentage === 0 ||
          percentage === 1 ||
          Math.round(percentage * 100) % 5 === 0
        ) {
          const percent = Math.round(percentage * 100);
          const details = args
            .filter((arg) => arg && typeof arg === 'string')
            .join(' ');
          console.log(`📦 ${percent}% ${message} ${details}`);
        }
      },
    }),
  ],
};

// Create webpack compiler with enhanced logging
const compiler = webpack(configWithProgress);

// Hook into compilation lifecycle
compiler.hooks.beforeCompile.tap('DevServerLogger', () => {
  console.log('\n🔨 Starting webpack compilation...');
  console.time('⏱️  Total Build Time');
});

compiler.hooks.afterCompile.tap('DevServerLogger', (compilation) => {
  console.timeEnd('⏱️  Total Build Time');

  // Show compilation stats
  const stats = compilation.getStats();
  console.log(`\n📊 Compilation Stats:`);
  console.log(`   • Modules: ${compilation.modules.size}`);
  console.log(`   • Chunks: ${compilation.chunks.size}`);
  console.log(`   • Assets: ${Object.keys(compilation.assets).length}`);

  // Show warnings and errors count
  const warnings = stats.compilation.warnings.length;
  const errors = stats.compilation.errors.length;
  if (warnings > 0) console.log(`   • ⚠️  Warnings: ${warnings}`);
  if (errors > 0) console.log(`   • ❌ Errors: ${errors}`);

  // Show largest modules (top 5)
  const modules = Array.from(compilation.modules)
    .filter((module) => module.size && module.size() > 0)
    .sort((a, b) => b.size() - a.size())
    .slice(0, 5);

  if (modules.length > 0) {
    console.log(`\n🐘 Largest modules:`);
    modules.forEach((module, index) => {
      const sizeKB = (module.size() / 1024).toFixed(1);
      const name = module.userRequest || module.identifier() || 'Unknown';
      // Clean up the path for readability
      const cleanName = name.replace(process.cwd(), '').replace(/\\/g, '/');
      console.log(`   ${index + 1}. ${sizeKB}KB - ${cleanName}`);
    });
  }

  console.log(`\n🚀 Server ready at http://${listenHost}:3000/\n`);
});

// Track rebuild performance
let rebuildCount = 0;
compiler.hooks.watchRun.tap('DevServerLogger', () => {
  rebuildCount++;
  console.log(`\n🔄 Rebuild #${rebuildCount} triggered...`);
  console.time('⚡ Rebuild Time');
});

compiler.hooks.done.tap('DevServerLogger', (stats) => {
  if (rebuildCount > 0) {
    console.timeEnd('⚡ Rebuild Time');

    // Show what changed (if available)
    if (stats.compilation.fileTimestamps) {
      const changedFiles = [];
      const { fileTimestamps } = stats.compilation;

      for (const [file, timestamp] of fileTimestamps) {
        if (timestamp && (!stats.startTime || timestamp > stats.startTime)) {
          changedFiles.push(
            file.replace(process.cwd(), '').replace(/\\/g, '/')
          );
        }
      }

      if (changedFiles.length > 0) {
        console.log(
          `📝 Changed files: ${changedFiles.slice(0, 3).join(', ')}${
            changedFiles.length > 3 ? ` +${changedFiles.length - 3} more` : ''
          }`
        );
      }
    }
  }
});

// Enhanced dev server configuration
const devServer = new WebpackDevServer(
  {
    host: listenHost,
    port: 3000,
    static: {
      directory: path.join(__dirname, ''),
      publicPath: config.output.publicPath,
    },
    hot: true,
    historyApiFallback: true,
    // Proxy is opt-in: set API_PROXY_TARGET to enable proxying /rest and /stomp
    // to a local backend (e.g., API_PROXY_TARGET=http://localhost:8080).
    // Without this, config.js determines the backend URL (default for human devs).
    ...(process.env.API_PROXY_TARGET
      ? {
          proxy: [
            {
              context: ['/rest', '/stomp'],
              target: process.env.API_PROXY_TARGET,
              changeOrigin: false,
              ws: true,
              cookieDomainRewrite: '',
              cookiePathRewrite: '/',
              onProxyRes: function (proxyRes) {
                var setCookie = proxyRes.headers['set-cookie'];
                if (setCookie) {
                  proxyRes.headers['set-cookie'] = setCookie.map(function (
                    cookie
                  ) {
                    return cookie
                      .replace(/;\s*SameSite=[^;]*/gi, '')
                      .replace(/;\s*Secure/gi, '');
                  });
                }
              },
            },
          ],
        }
      : {}),

    // Enhanced dev middleware options
    devMiddleware: {
      stats: {
        colors: true,
        hash: false,
        version: false,
        timings: true,
        assets: false,
        chunks: false,
        modules: false,
        reasons: false,
        children: false,
        source: false,
        errors: true,
        errorDetails: true,
        warnings: true,
        publicPath: false,
        builtAt: true,
      },
    },

    // Client-side logging
    client: {
      logging: 'warn',
      overlay: {
        errors: true,
        warnings: false,
      },
      progress: false, // We handle progress server-side
    },
  },
  compiler
);

devServer.startCallback((err) => {
  if (err) {
    console.error('❌ Dev server failed to start:', err);
    return;
  }

  console.log('🌟 Webpack Dev Server started successfully!');
  console.log(`📡 Listening at http://${listenHost}:3000/`);
  console.log('🔍 Watching for changes...\n');
});
