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

class BDPUtils {
  


	def resetDocsisModemTable(log, testCase, context){

		

		def groovyUtils = new com.eviware.soapui.support.GroovyUtils(context)
		GroovyScriptEngine gse = new GroovyScriptEngine(groovyUtils.projectPath + "/scripts/") 
		def externalScript = gse.loadScriptByName("UtilsTest.Groovy")  
		// Create a runtime instance of script
		def instance = externalScript.newInstance()

		// Sanity check 
		assert instance != null

		def project = testCase.testSuite.project
		def dbBdp  = project.getPropertyValue('dbBdp')
		def dbHost = project.getPropertyValue('dbHost')
		def user = project.getPropertyValue('dbUser')
		def password = project.getPropertyValue('dbPass')
		log.info(user + ":" + password + "@" + dbHost +"/" + dbBdp )
		
		def BdpInstance = instance.getInstanceDB(dbBdp,dbHost,user,password,log)
		
		BdpInstance.execute("TRUNCATE TABLE docsis_modem;");
		
		BdpInstance.close()
	}



}