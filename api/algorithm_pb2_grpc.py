# Generated by the gRPC Python protocol compiler plugin. DO NOT EDIT!
"""Client and server classes corresponding to protobuf-defined services."""
import grpc

from api import algorithm_pb2 as algorithm__pb2


class AlgorithmStub(object):
    """The greeting service definition.
    """

    def __init__(self, channel):
        """Constructor.

        Args:
            channel: A grpc.Channel.
        """
        self.ExecuteAlgorithm = channel.unary_unary(
                '/algorithm.Algorithm/ExecuteAlgorithm',
                request_serializer=algorithm__pb2.AlgorithmRequest.SerializeToString,
                response_deserializer=algorithm__pb2.AlgorithmReply.FromString,
                )
        self.SparkQueryState = channel.unary_unary(
                '/algorithm.Algorithm/SparkQueryState',
                request_serializer=algorithm__pb2.SparkQueryRequest.SerializeToString,
                response_deserializer=algorithm__pb2.SparkQueryReply.FromString,
                )


class AlgorithmServicer(object):
    """The greeting service definition.
    """

    def ExecuteAlgorithm(self, request, context):
        """Sends a greeting
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def SparkQueryState(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')


def add_AlgorithmServicer_to_server(servicer, server):
    rpc_method_handlers = {
            'ExecuteAlgorithm': grpc.unary_unary_rpc_method_handler(
                    servicer.ExecuteAlgorithm,
                    request_deserializer=algorithm__pb2.AlgorithmRequest.FromString,
                    response_serializer=algorithm__pb2.AlgorithmReply.SerializeToString,
            ),
            'SparkQueryState': grpc.unary_unary_rpc_method_handler(
                    servicer.SparkQueryState,
                    request_deserializer=algorithm__pb2.SparkQueryRequest.FromString,
                    response_serializer=algorithm__pb2.SparkQueryReply.SerializeToString,
            ),
    }
    generic_handler = grpc.method_handlers_generic_handler(
            'algorithm.Algorithm', rpc_method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))


 # This class is part of an EXPERIMENTAL API.
class Algorithm(object):
    """The greeting service definition.
    """

    @staticmethod
    def ExecuteAlgorithm(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/algorithm.Algorithm/ExecuteAlgorithm',
            algorithm__pb2.AlgorithmRequest.SerializeToString,
            algorithm__pb2.AlgorithmReply.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def SparkQueryState(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/algorithm.Algorithm/SparkQueryState',
            algorithm__pb2.SparkQueryRequest.SerializeToString,
            algorithm__pb2.SparkQueryReply.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)


class DataPipeStub(object):
    """Missing associated documentation comment in .proto file."""

    def __init__(self, channel):
        """Constructor.

        Args:
            channel: A grpc.Channel.
        """
        self.Replay = channel.unary_unary(
                '/algorithm.DataPipe/Replay',
                request_serializer=algorithm__pb2.ReplayRequest.SerializeToString,
                response_deserializer=algorithm__pb2.ReplayReply.FromString,
                )


class DataPipeServicer(object):
    """Missing associated documentation comment in .proto file."""

    def Replay(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')


def add_DataPipeServicer_to_server(servicer, server):
    rpc_method_handlers = {
            'Replay': grpc.unary_unary_rpc_method_handler(
                    servicer.Replay,
                    request_deserializer=algorithm__pb2.ReplayRequest.FromString,
                    response_serializer=algorithm__pb2.ReplayReply.SerializeToString,
            ),
    }
    generic_handler = grpc.method_handlers_generic_handler(
            'algorithm.DataPipe', rpc_method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))


 # This class is part of an EXPERIMENTAL API.
class DataPipe(object):
    """Missing associated documentation comment in .proto file."""

    @staticmethod
    def Replay(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/algorithm.DataPipe/Replay',
            algorithm__pb2.ReplayRequest.SerializeToString,
            algorithm__pb2.ReplayReply.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)
