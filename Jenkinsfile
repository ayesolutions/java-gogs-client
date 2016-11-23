node('master') {
    def javaTool = tool name: 'oracle-jdk-8u112', type: 'jdk'
    env.JAVA_HOME = "${javaTool}"
    env.PATH = "${javaTool}/bin:${env.PATH}"

    checkout scm

    stage('Build') {
        sh './gradlew clean classes'
    }

    stage('Test') {
        sh './gradlew test jacocoTestReport pmdMain javadoc jdependMain findbugsMain checkstyleMain cpdCheck'
        junit '**/test-results/*.xml'
        step([$class: 'JacocoPublisher'])
        step([$class: 'JavadocArchiver', javadocDir: 'build/docs/javadoc', keepAll: true])
        step([$class: 'CheckStylePublisher', canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/checkstyle/*.xml', unHealthy: ''])
        step([$class: 'FindBugsPublisher', canComputeNew: false, defaultEncoding: '', excludePattern: '', healthy: '', includePattern: '', pattern: '**/findbugs/*.xml', unHealthy: ''])
        step([$class: 'PmdPublisher', canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/pmd/*.xml', unHealthy: ''])
        step([$class: 'DryPublisher', canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/cpd/cpdCheck.xml', unHealthy: ''])
        step([$class: 'WarningsPublisher', canComputeNew: false, canResolveRelativePaths: false, consoleParsers: [[parserName: 'Java Compiler (javac)']], defaultEncoding: '', excludePattern: '', healthy: '', includePattern: '', messagesPattern: '', unHealthy: ''])
        step([$class: 'AnalysisPublisher', canComputeNew: false, defaultEncoding: '', healthy: '', unHealthy: ''])
    }

    stage('Package') {
        sh './gradlew jar'
    }
}