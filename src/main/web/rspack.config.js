export default {
    entry: {
        main: './src/main/web/script.js',
    },
    output: {
        path: './src/main/resources/static',
        chunkFilename: '[id].js'
    },
    module: {
        rules: [
            {
                test: /\.css$/,
                use: [{
                    loader: "postcss-loader",
                    options: {
                        postcssOptions: {
                            plugins: {
                                "@tailwindcss/postcss": {},
                            },
                        }
                    }
                }],
                type: "css"
            },
        ]
    },
    experiments: {
        css: true,
    }
}
