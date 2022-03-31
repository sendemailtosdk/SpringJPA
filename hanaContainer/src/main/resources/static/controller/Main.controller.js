sap.ui.define(
["sap/ui/core/mvc/Controller",
    "jquery.sap.global",
    "srini/util/service",
    "sap/m/MessageToast"

],
function(Controller, jQuery,service,MessageToast) {

    return Controller.extend("srini.controller.Main", {
            onInit:function(oEvent){
           
            var oModel = new sap.ui.model.json.JSONModel({
                         "vendor":[],
                         "postPayload":{
                        "companyName":"",
                        "firstName":"",
                        "lastName":"",
                        "website":"",
                        "email":"",
                        "status": "Active",
                        "gstNo":""
                         
                         }
            });
            
             sap.ui.getCore().setModel(oModel);
          
            },
            
            handleLoadData: function(oEvent) {
            //    alert("Load initiated soon");
                 var that = this;
                 service.callService("/vendor","GET",{}).then(function(data){
                    sap.ui.getCore().getModel().setProperty("/vendor",data);
                    var oTable = that.getView().byId("idTable");
                    oTable.bindRows("/vendor");
                 }).catch(function(err){
                 
                 })

   
   
        },
        handleSave:function(oEvent){
        
                var that = this;
                var postPayload =  sap.ui.getCore().getModel().getProperty("/postPayload");
                 service.callService("/vendor","POST",postPayload).then(function(data){
                  MessageToast.show("Successfully saved");
                    that.handleLoadData( );
                 }).catch(function(err){
                  MessageToast.show(err);
                 })
        
        }
    });

});