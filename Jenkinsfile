pipeline{

	agent any

	tools{
		maven 'maven'
		}

	stages{
		stage("Build"){
		     steps
		      {
			git 'https://github.com/jglick/simple-maven-project-with-tests'
			bat "mvn -Dmaven.test.failure.ignore=true clean package"
		      }
		      post
		      {
			success
			{
			junit '**/target/surefire-reports/TEST-*.xml'
			archiveArtifacts 'target/*.jar'
			}
		    }
		}		

		stage("Deploy to QA"){
		     steps{
			echo("deploy to qa")
		      }
		}

		stage('Regression Automation Tests'){
		     steps{
			catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE'){
			git 'https://github.com/MitaJha/MyPomSereisFrameWork.git'
			bat "mvn clean test -Dsurefire.suiteXmlFiles=srcTestResources\testrunners\testng_regression.xml"
		      }
		   }
		}

		stage('Publish Allure Reports'){
		     steps{
			script{
			    allure([
				includeProperties: false,
				jdk: '',
				properties: [],
				reportBuildPolicy: 'ALWAYS',
				results: [[path: '/allure-rsults']]
				])
			 }
			
		      }
		}

		stage('Publish Extent Report'){
		     steps{
			    publishHTML([
				allowMissing: false,
				alwaysLinkToLastBuild: false,
				keepAll: true,
				reportDir: 'reports',
				reportFiles: 'TestExecutionReport.html',
				reportTitle: ''
				])
			 }
			
		      }

		stage("Deploy to stage"){
		     steps{
			echo("deploy to stage")
		      }
		}
		
		stage('Sanity Automation Tests'){
		     steps{
			catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE'){
			git 'https://github.com/MitaJha/MyPomSereisFrameWork.git'
			bat "mvn clean test -Dsurefire.suiteXmlFiles=srcTestResources\testrunners\testng_sanity.xml"
		      }
		   }
		}

		stage('Publish sanity Extent Report'){
		     steps{
			    publishHTML([
				allowMissing: false,
				alwaysLinkToLastBuild: false,
				keepAll: true,
				reportDir: 'reports',
				reportFiles: 'TestExecutionReport.html',
				reportTitle: ''
				])
			 }
			
		      }
		}


 	}