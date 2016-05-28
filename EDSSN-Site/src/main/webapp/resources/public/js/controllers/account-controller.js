angular.module('edssnApp')
        .controller('AccountController', ['$scope', 'UserService', function ($scope, UserService) {
                $scope.awardPoints = [];

                $scope.getAwardPoints = function () {
                    UserService.getAwardPointsForCurrentUser().then(function (awardPoints) {
                        $scope.awardPoints = awardPoints;
                    });
                };
                $scope.getAwardPoints();

                $scope.assignCoupon = function () {
                    UserService.assignCoupon($scope.vm.coupon).then(function () {
                        $scope.getAwardPoints();
                    });
                };
            }]);
