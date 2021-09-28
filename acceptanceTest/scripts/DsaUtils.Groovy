// Import the Groovy class required to work with SQL databases.
import groovy.sql.Sql
import com.eviware.soapui.support.XmlHolder
import net.sf.*;
import net.sf.json.*;
import net.sf.json.groovy.*;
import groovy.lang.Binding
import groovy.util.GroovyScriptEngine
import groovy.sql.Sql
import java.sql.ResultSet

class DsaUtils {
  

	def assertResetDevice(deviceId, occurr, deviceType , log, messageExchange, context){

		log.info("Comprobando si " + deviceId + 	" se reinicio");


		def groovyUtils = new com.eviware.soapui.support.GroovyUtils(context)
		GroovyScriptEngine gse = new GroovyScriptEngine(groovyUtils.projectPath + "/scripts/") 
		def externalScript = gse.loadScriptByName("UtilsTest.Groovy")  
		// Create a runtime instance of script
		def instance = externalScript.newInstance()

		// Sanity check 
		assert instance != null
		assert deviceId != null

		def project = messageExchange.modelItem.testStep.testCase.testSuite.project
		def dbDsa  = project.getPropertyValue('dbDSA')
		def dbHost = project.getPropertyValue('dbHost')
		def user = project.getPropertyValue('dbUser')
		def password = project.getPropertyValue('dbPass')
		log.info(user + ":" + password + "@" + dbHost +"/" + dbDsa )
		
		def dsaInstance = instance.getInstanceDB(dbDsa,dbHost,user,password,log)
		// , 'SELECT cpe4_tag_id FROM docsis_params where cm_mac=\'ff00ff00ff00\';'

		dsaInstance.query("SELECT count(*) as total FROM logs where action='device." + deviceType + ".reset' AND user='" + deviceId + "';"){ ResultSet rs ->

				// Pruebo el primero
				rs.next()
				log.info("Cantida de registros : " + rs.getString('total'))

				assert rs.getInt('total') == occurr
			}		
		dsaInstance.close()


	}

	def resetLogTable(log, testCase, context){

		

		def groovyUtils = new com.eviware.soapui.support.GroovyUtils(context)
		GroovyScriptEngine gse = new GroovyScriptEngine(groovyUtils.projectPath + "/scripts/") 
		def externalScript = gse.loadScriptByName("UtilsTest.Groovy")  
		// Create a runtime instance of script
		def instance = externalScript.newInstance()

		// Sanity check 
		assert instance != null

		def project = testCase.testSuite.project
		def dbDsa  = project.getPropertyValue('dbDSA')
		def dbHost = project.getPropertyValue('dbHost')
		def user = project.getPropertyValue('dbUser')
		def password = project.getPropertyValue('dbPass')
		log.info(user + ":" + password + "@" + dbHost +"/" + dbDsa )
		
		def dsaInstance = instance.getInstanceDB(dbDsa,dbHost,user,password,log)
		
		dsaInstance.execute("TRUNCATE TABLE logs;");
		
		dsaInstance.close()
	}



}
