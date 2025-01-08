package com.usp.mba.domain.enums;

public enum Technology {
    // Linguagens de Programação
    JAVA,
    PYTHON,
    JAVASCRIPT,
    TYPESCRIPT,
    C,
    C_PLUS_PLUS,
    C_SHARP,
    RUBY,
    PHP,
    KOTLIN,
    SWIFT,
    GO,
    RUST,
    SCALA,
    PERL,
    HASKELL,
    DART,
    OBJECTIVE_C,

    // Frameworks e Bibliotecas Frontend
    REACT,
    ANGULAR,
    VUE_JS,
    SVELTE,
    NEXT_JS,
    NUXT_JS,
    BOOTSTRAP,
    TAILWIND_CSS,

    // Frameworks e Bibliotecas Backend
    SPRING_BOOT,
    DJANGO,
    FLASK,
    EXPRESS_JS,
    NEST_JS,
    RUBY_ON_RAILS,
    ASP_NET_CORE,
    LARAVEL,
    SYMFONY,
    FASTAPI,

    // Bancos de Dados Relacionais
    MYSQL,
    POSTGRESQL,
    ORACLE_DB,
    SQL_SERVER,
    SQLITE,
    DB2,

    // Bancos de Dados Não Relacionais
    MONGODB,
    REDIS,
    CASSANDRA,
    COUCHBASE,
    DYNAMODB,
    NEO4J,
    ELASTICSEARCH,

    // Ferramentas de DevOps
    DOCKER,
    KUBERNETES,
    JENKINS,
    GITHUB_ACTIONS,
    CIRCLECI,
    TRAVISCI,
    TERRAFORM,
    ANSIBLE,
    PUPPET,
    CHEF,

    // Nuvens e Infraestrutura
    AWS,
    AZURE,
    GOOGLE_CLOUD,
    HEROKU,
    DIGITALOCEAN,
    OPENSHIFT,

    // Data Science e Machine Learning
    TENSORFLOW,
    PYTORCH,
    SCIKIT_LEARN,
    NUMPY,
    PANDAS,
    KERAS,
    SPARK,
    HADOOP,
    DASK,

    // Tecnologias Mobile
    ANDROID,
    IOS,
    FLUTTER,
    REACT_NATIVE,
    XAMARIN,

    // Outras Tecnologias
    GRAPHQL,
    REST_API,
    GRPC,
    WEBASSEMBLY,
    KAFKA,
    RABBITMQ,
    OPENAPI,
    SWAGGER;

    @Override
    public String toString() {
        return name().replace("_", " ");
    }
}
