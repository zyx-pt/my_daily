$scope.downloadFile = function(fileType){
    var triggerDelay= 500;
    var files = [];
    if (fileType === 'billFile'){
        files = $scope.orderVo.billFileList;
    }
    if (fileType === 'bookEntrustFile'){
        files = $scope.orderVo.bookEntrustList;
    }
    if(files && files.length) {
        for(var i in files) {
            $scope.downloadFileByUrl(files[i],i * triggerDelay)
        }
    }
};
$scope.downloadFileByUrl = function(url,triggerDelay){
    setTimeout(function() {
        var alink = document.createElement("a");
        alink.href = url;
        document.body.appendChild(alink);
        alink.click();
    }, triggerDelay);
};